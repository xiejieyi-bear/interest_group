<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">    
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('common.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" :label="$t('user_table.id')" width="150px">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>  
      
      <el-table-column width="150px" align="center" :label="$t('user_table.name')">
        <template slot-scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('user_table.telphone')">
        <template slot-scope="scope">
          <span>{{scope.row.telphone}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('user_table.balance')">
        <template slot-scope="scope">
          <span>{{scope.row.balance}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('user_table.actions')" min-width="350" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleDelete(scope.row)">{{$t('common.delete')}}</el-button> 
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('common.edit')}}</el-button>
          <el-button type="primary" size="mini" style="width:100px" @click="showChargeHistory(scope.row)">{{$t('user_table.charge_history')}}</el-button>
          <el-button type="primary" size="mini" style="width:100px" @click="showConsumeHistory(scope.row)">{{$t('user_table.consume_history')}}</el-button>
        </template>
      </el-table-column>
    </el-table>    

    <el-dialog title="创建用户" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="temp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('user_table.id')" prop="id">
          <el-input v-model="temp.id"></el-input>
        </el-form-item>

        <el-form-item :label="$t('user_table.name')" prop="name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>

        <el-form-item :label="$t('user_table.telphone')" prop="telphone">
          <el-input v-model="temp.telphone"></el-input>
        </el-form-item>

        <el-form-item :label="$t('user_table.balance')" prop="balance">
          <el-input v-model="temp.balance"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{$t('table.cancel')}}</el-button>
        <el-button type="primary" @click="createData">{{$t('table.confirm')}}</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { fetchUserList, createUser, deleteUser } from '@/api/user'
export default {
  name: 'userTable',
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      dialogFormVisible: false,
      temp: {
        id: undefined,
        name: '',
        balance: '',
        telphone: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchUserList(this.listQuery).then(response => {
        this.list = response.data.items
        // this.total = response.data.total
        this.listLoading = false
      })
    },

    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        balance: '',
        telphone: ''
      }
    },

    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          // this.temp.author = 'vue-element-admin'
          createUser(this.temp).then(() => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      this.temp = Object.assign({}, row) // copy obj
      deleteUser(this.temp).then(() => {
        this.getList()
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      })
    }
  }
}
</script>
