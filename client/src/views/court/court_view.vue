<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">    
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('common.add')}}</el-button>
    </div>

  <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" :label="$t('court_table.id')" width="40">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150px" align="center" :label="$t('court_table.name')">
        <template slot-scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150px" :label="$t('court_table.addr')">
        <template slot-scope="scope">
          <span>{{scope.row.addr}}</span>
        </template>
      </el-table-column>
      <el-table-column width="80px" align="center" :label="$t('court_table.charge')">
        <template slot-scope="scope">
          <span>{{scope.row.charge}}</span>
        </template>
      </el-table-column>

      <el-table-column width="80px" align="center" :label="$t('court_table.balance')">
        <template slot-scope="scope">
          <span>{{scope.row.balance}}</span>
        </template>
      </el-table-column>
      
      <el-table-column width="150px" align="center" :label="$t('court_table.telphone')">
        <template slot-scope="scope">
          <span>{{scope.row.telphone}}</span>
        </template>
      </el-table-column>
    

      <el-table-column align="center" :label="$t('court_table.actions')" min-width="350" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleDelete(scope.row)">{{$t('common.delete')}}</el-button>         

          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('common.edit')}}</el-button>
          <el-button type="primary" size="mini" style="width:100px" @click="showChargeHistory(scope.row)">{{$t('court_table.charge_history')}}</el-button>          
        </template>
      </el-table-column>
    </el-table>    

    <el-dialog title="创建场地" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="temp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('court_table.name')" prop="name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>

        <el-form-item :label="$t('court_table.addr')" prop="addr">
          <el-input type="textarea" v-model="temp.addr"></el-input>
        </el-form-item>

        <el-form-item :label="$t('court_table.charge')" prop="charge">
          <el-input v-model="temp.charge"></el-input>
        </el-form-item>

        <el-form-item :label="$t('court_table.telphone')" prop="telphone">
          <el-input v-model="temp.telphone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{$t('table.cancel')}}</el-button>
        <el-button type="primary" @click="createData">{{$t('table.confirm')}}</el-button>
      </div>
    </el-dialog>

    <el-dialog width='800px' title="资金明细" :visible.sync="dialogChargeVisible">
        <el-table :key='chargeTableKey' :data="chargeList" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">       
            <el-table-column width="150px" align="center" :label="$t('court_charge_table.name')">
                <template slot-scope="scope">
                  <span>{{scope.row.name}}</span>
                </template>
            </el-table-column>
            <el-table-column width="100px" :label="$t('court_charge_table.type')">
                <template slot-scope="scope">
                  <span>{{scope.row.type}}</span>
                </template>
            </el-table-column>
            <el-table-column width="150px" :label="$t('court_charge_table.time')">
                <template slot-scope="scope">
                  <span>{{scope.row.time}}</span>
                </template>
            </el-table-column>
            <el-table-column width="110px" align="center" :label="$t('court_charge_table.charge')">
                <template slot-scope="scope">
                  <span>{{scope.row.charge}}</span>
                </template>
            </el-table-column>

            <el-table-column min-width="150px" align="center" :label="$t('court_charge_table.desc')">
                <template slot-scope="scope">
                  <span>{{scope.row.desc}}</span>
                </template>
            </el-table-column>      
        </el-table>   
    </el-dialog>   

  </div>
</template>

<script>
import { fetchCourtList, createCourt, deleteCourt, getChargeHistory } from '@/api/court'
export default {
  name: 'complexTable',
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
        addr: '',
        charge: '',
        telphone: ''
      },

      chargeTableKey: 1,
      chargeList: null,
      dialogChargeVisible: false

    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchCourtList(this.listQuery).then(response => {
        this.list = response.data.items
        // this.total = response.data.total
        this.listLoading = false
      })
    },

    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        addr: '',
        charge: '',
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
          createCourt(this.temp).then(() => {
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
      deleteCourt(this.temp).then(() => {
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
      getChargeHistory(this.temp.id).then(response => {
        this.chargeList = response.data.items
        // this.total = response.data.total
        this.listLoading = false
      })
    }

    // handleUpdate(row) {
    //   this.temp = Object.assign({}, row) // copy obj
    //   this.temp.timestamp = new Date(this.temp.timestamp)
    //   this.dialogStatus = 'update'
    //   this.dialogFormVisible = true
    //   this.$nextTick(() => {
    //     this.$refs['dataForm'].clearValidate()
    //   })
    // },
    // updateData() {
    //   this.$refs['dataForm'].validate((valid) => {
    //     if (valid) {
    //       const tempData = Object.assign({}, this.temp)
    //       tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
    //       updateArticle(tempData).then(() => {
    //         for (const v of this.list) {
    //           if (v.id === this.temp.id) {
    //             const index = this.list.indexOf(v)
    //             this.list.splice(index, 1, this.temp)
    //             break
    //           }
    //         }
    //         this.dialogFormVisible = false
    //         this.$notify({
    //           title: '成功',
    //           message: '更新成功',
    //           type: 'success',
    //           duration: 2000
    //         })
    //       })
    //     }
    //   })
    // },
    // handleDelete(row) {
    //   this.$notify({
    //     title: '成功',
    //     message: '删除成功',
    //     type: 'success',
    //     duration: 2000
    //   })
    //   const index = this.list.indexOf(row)
    //   this.list.splice(index, 1)
    // },
    // handleFetchPv(pv) {
    //   fetchPv(pv).then(response => {
    //     this.pvData = response.data.pvData
    //     this.dialogPvVisible = true
    //   })
    // },
    // handleDownload() {
    //   this.downloadLoading = true
    //   import('@/vendor/Export2Excel').then(excel => {
    //     const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
    //     const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
    //     const data = this.formatJson(filterVal, this.list)
    //     excel.export_json_to_excel(tHeader, data, 'table-list')
    //     this.downloadLoading = false
    //   })
    // },
    // formatJson(filterVal, jsonData) {
    //   return jsonData.map(v => filterVal.map(j => {
    //     if (j === 'timestamp') {
    //       return parseTime(v[j])
    //     } else {
    //       return v[j]
    //     }
    //   }))
    // }
  }
}
</script>
