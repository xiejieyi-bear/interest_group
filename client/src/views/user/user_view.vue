<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">    
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('common.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" :label="$t('user_table.usermark')" width="100px">
        <template slot-scope="scope">
          <span>{{scope.row.usermark}}</span>
        </template>
      </el-table-column>  
      
      <el-table-column width="150px" align="center" :label="$t('user_table.username')">
        <template slot-scope="scope">
          <span>{{scope.row.username}}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" :label="$t('user_table.telephone')">
        <template slot-scope="scope">
          <span>{{scope.row.telephone}}</span>
        </template>
      </el-table-column>

      <el-table-column width="80px" :label="$t('user_table.charge_total')">
        <template slot-scope="scope">
          <span>{{scope.row.charge_total}}</span>
        </template>
      </el-table-column>

      <el-table-column width="80px" :label="$t('user_table.consume_total')">
        <template slot-scope="scope">
          <span>{{scope.row.consume_total}}</span>
        </template>
      </el-table-column>

      <el-table-column width="80px" :label="$t('user_table.balance')">
        <template slot-scope="scope">
          <span>{{scope.row.balance}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('user_table.actions')" min-width="400" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleDelete(scope.row)">{{$t('common.delete')}}</el-button> 
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('common.edit')}}</el-button>
          <el-button type="primary" size="mini" style="width:100px" @click="showChargeHistory(scope.row)">{{$t('user_table.charge_history')}}</el-button>
          <el-button type="primary" size="mini" style="width:100px" @click="showConsumeHistory(scope.row)">{{$t('user_table.consume_history')}}</el-button>
        </template>
      </el-table-column>
    </el-table>

   <!--  <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div> -->

    <el-dialog title="创建用户" :visible.sync="dialogFormVisible">
      <el-form :rules="user_rules" ref="dataForm" :model="temp" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('user_table.usermark')" prop="usermark">
          <el-input v-model="temp.usermark"></el-input>
        </el-form-item>

        <el-form-item :label="$t('user_table.username')" prop="username">
          <el-input v-model="temp.username"></el-input>
        </el-form-item>

        <el-form-item :label="$t('user_table.password')" prop="password">
          <el-input v-model="temp.password" type="password" ></el-input>
        </el-form-item>

        <el-form-item :label="$t('user_table.telephone')" prop="telephone">
          <el-input v-model="temp.telephone"></el-input>
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

    <el-dialog width='800px' title="充值记录" :visible.sync="dialogChargeVisible">
        <el-table :key='chargeTableKey' :data="chargeList" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">       
            <el-table-column width="150px" align="center" :label="$t('user_charge_table.username')">
                <template slot-scope="scope">
                  <span>{{scope.row.username}}</span>
                </template>
            </el-table-column>            
            <el-table-column width="150px" :label="$t('user_charge_table.time')">
                <template slot-scope="scope">
                  <span>{{scope.row.time}}</span>
                </template>
            </el-table-column>
            <el-table-column width="110px" align="center" :label="$t('user_charge_table.charge')">
                <template slot-scope="scope">
                  <span>{{scope.row.charge}}</span>
                </template>
            </el-table-column>

            <el-table-column min-width="150px" align="center" :label="$t('user_charge_table.desc')">
                <template slot-scope="scope">
                  <span>{{scope.row.desc}}</span>
                </template>
            </el-table-column>      
        </el-table>   
    </el-dialog>

    <el-dialog width='800px' title="消费记录" :visible.sync="dialogConsumeVisible">
        <el-table :key='consumeTableKey' :data="consumeList" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">       
            <el-table-column width="150px" align="center" :label="$t('user_consumer_table.username')">
                <template slot-scope="scope">
                  <span>{{scope.row.username}}</span>
                </template>
            </el-table-column>            
            <el-table-column width="150px" :label="$t('user_consumer_table.time')">
                <template slot-scope="scope">
                  <span>{{scope.row.time}}</span>
                </template>
            </el-table-column>
            <el-table-column width="110px" align="center" :label="$t('user_consumer_table.location')">
                <template slot-scope="scope">
                  <span>{{scope.row.location}}</span>
                </template>
            </el-table-column>

            <el-table-column min-width="80px" align="center" :label="$t('user_consumer_table.self_num')">
                <template slot-scope="scope">
                  <span>{{scope.row.self_num}}</span>
                </template>
            </el-table-column>

            <el-table-column min-width="80px" align="center" :label="$t('user_consumer_table.participate_total')">
                <template slot-scope="scope">
                  <span>{{scope.row.participate_total}}</span>
                </template>
            </el-table-column>   

            <el-table-column min-width="80px" align="center" :label="$t('user_consumer_table.consume')">
                <template slot-scope="scope">
                  <span>{{scope.row.consume}}</span>
                </template>
            </el-table-column>         
        </el-table>   
    </el-dialog>  

  </div>
</template>

<script>
import { fetchUserList, createUser, deleteUser, getUserChargeHistory, getUserConsumeHistory } from '@/api/user'
export default {

  name: 'userTable',
  data() {
    const validate = (rule, value, callback) => {
      if (value.length >= 20) {
        callback(new Error('请输入小于8个字符'))
      } else {
        callback()
      }
    }
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      dialogFormVisible: false,
      temp: {
        usermark: undefined,
        username: '',
        balance: '',
        telephone: ''
      },
      user_rules: {
        //  name: [
        //    { required: true, message: '请输入活动名称', trigger: 'blur' },
        //    { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        //  ],
        usermark: [{ required: true, trigger: 'change', validator: validate }],
        username: [{ required: true, trigger: 'change', validator: validate }],
        telephone: [{ max: 12, message: '长度在 0 到 12 个字符', trigger: 'change' }],
        balance: [{ trigger: 'change', validator: validate }]
      },

      chargeTableKey: 1,
      chargeList: null,
      dialogChargeVisible: false,

      dialogConsumeVisible: false,
      consumeTableKey: 2,
      consumeList: null

    }
  },

  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchUserList(this.listQuery).then(response => {
        this.list = response.data.data
        // this.total = response.data.total
        this.listLoading = false
      })
    },

    resetTemp() {
      this.temp = {
        usermark: undefined,
        username: '',
        balance: '',
        telephone: ''
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
          // this.temp.usermark = parseInt(Math.random() * 100) + 1024 // mock a usermark
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
      deleteUser(this.temp.usermark).then(() => {
        this.getList()
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    showChargeHistory(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogChargeVisible = true
      getUserChargeHistory(this.temp.usermark).then(response => {
        this.chargeList = response.data.items
        // this.total = response.data.total
        // this.listLoading = false
      })
    },
    showConsumeHistory(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogConsumeVisible = true
      getUserConsumeHistory(this.temp.usermark).then(response => {
        this.consumeList = response.data.items
        // this.total = response.data.total
        // this.listLoading = false
      })
    }

  }
}
</script>
