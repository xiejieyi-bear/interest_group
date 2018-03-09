<template>
  <div class="app-container calendar-list-container">

    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" @click="showCreateDialog" type="primary" icon="el-icon-edit">{{$t('common.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">

      <el-table-column width="150px" align="center" :label="$t('activity_table.begin_time')">
        <template slot-scope="scope">
          <span>{{scope.row.beginTime}}</span>
        </template>
      </el-table-column>

       <el-table-column width="120px" align="center" :label="$t('activity_table.duration')">
        <template slot-scope="scope">
          <span>{{scope.row.duration}}</span>
        </template>
      </el-table-column>


      <el-table-column width="150px" :label="$t('activity_table.court_name')">
        <template slot-scope="scope">
          <span>{{scope.row.courtName}}</span>
        </template>
      </el-table-column>

      <el-table-column width="100px" :label="$t('activity_table.chargeTotal')">
        <template slot-scope="scope">
          <span>{{scope.row.chargeTotal}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100px" :label="$t('activity_table.participate_total')">
        <template slot-scope="scope">
          <span>{{scope.row.participateTotal}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('activity_table.participate_users')">
        <template slot-scope="scope">
          <span>{{scope.row.participateUsers}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('activity_table.remark')">
        <template slot-scope="scope">
          <span>{{scope.row.remark}}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" :label="$t('activity_table.activityState')">
        <template slot-scope="scope">
          <span>{{scope.row.state? "已结束":"报名中"}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('table.actions')" min-width="200px"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-show="scope.row.state==1" type="primary" size="mini"
                     @click="handleDelete(scope.row)">{{$t('common.delete')
            }}</el-button>
          <el-button v-show="scope.row.state==0 || scope.row.state == 2" type="primary" size="mini"
                     @click="handleSettlement(scope.row)">{{$t('activity_table.settlement')
            }}</el-button>

          <el-button v-show="scope.row.state==0" type="primary" size="mini"
                     @click="cancelActivity(scope.row)">{{$t('activity_table.cancel')
            }}</el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog title="创建活动" :visible.sync="dialogFormVisible">
      <el-form :rules="activity_rules" ref="dataForm" :model="temp" label-position="left" label-width="150px"
               style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('activity_table.begin_time')" prop="begin_time">
          <el-date-picker v-model="temp.beginTime" type="datetime" format="yyyy-MM-dd HH:mm" placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item :label="$t('activity_table.duration')" prop="duration">
          <el-select class="filter-item" v-model="temp.duration" placeholder="Please select">
            <el-option v-for="item in  activity_times_options" :key="item.key" :label="item.display_name" :value="item.key">
            </el-option>
          </el-select>

        </el-form-item>

        <el-form-item :label="$t('activity_table.court_name')" prop="court_name">
          <el-select class="filter-item" v-model="temp.courtID" placeholder="Please select">
            <el-option v-for="item in  activity_court_options" :key="item.key" :label="item.display_name" :value="item.key">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="场地数量:">
          <el-input-number v-model="temp.courtNums"  size="mini" :min="1" :max="10" label="场地数量"></el-input-number>
        </el-form-item>

        <el-form-item :label="$t('activity_table.chargeTotal')" prop="chargeTotal">
          <el-input type="number" max=1000 v-model.number="temp.chargeTotal"  ></el-input>
        </el-form-item>

        <el-form-item :label="$t('activity_table.remark')" prop="remark">
          <el-input v-model="temp.remark"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createActivity">{{$t('table.confirm')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { fetchActivityList, create_activity, fetchCourtList } from '@/api/court'
  import { activitySettle } from '@/api/user'
  import { formatDateYYMMDDHHMM } from '@/utils/common'

  export default {
    name: 'activityTable',
    data() {
      return {
        tableKey: 2,
        list: null,
        total: null,
        listLoading: true,
        dialogFormVisible: false,
        temp: {
          beginTime: undefined,
          duration: 0,
          courtName: undefined,
          chargeTotal: 0,
          courtNums: 0,
          remark: ''
        },

        activity_rules: {
          chargeTotal: [{ required: true, type: 'number', trigger: 'change', max: 1000 }]
        },
        courts: [],
        activity_court_options: [],
        activity_times_options: [
          { key: 30, display_name: '30分钟' },
          { key: 60, display_name: '60分钟' },
          { key: 90, display_name: '90分钟' },
          { key: 120, display_name: '120分钟' },
          { key: 150, display_name: '150分钟' },
          { key: 180, display_name: '180分钟' }
        ]
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        fetchActivityList(this.listQuery).then(response => {
          const activityList = response.data.data
          this.list = []
          for (const item of activityList) {
            const activityItem = {}

            activityItem.chargeTotal = item.chargeTotal
            activityItem.state = item.state
            activityItem.remark = item.remark
            // 默认选第一个，暂时不支持多个

            if (item.courts.length >= 1) {
              const court = item.courts[0]
              const beginTime = court.beginTime
              activityItem.beginTime = formatDateYYMMDDHHMM(new Date(beginTime))
              activityItem.duration = court.duration
              activityItem.courtName = court.courtName
              activityItem.courtNums = court.courtNums
            }

            const users = item.participates
            let participates_len = 0
            const usernames = []
            for (const user of users) {
              participates_len = user.participateNumbers + participates_len

              if (user.participateNumbers > 1) {
                user.username = user.username + '*' + user.participateNumbers
              }
              usernames.push(user.username)
            }
            activityItem.participateTotal = participates_len
            activityItem.participateUsers = usernames.join(',')
            activityItem.id = item.id
            this.list.push(activityItem)
          }

          // this.total = response.data.total
          this.listLoading = false
        })
      },

      handleSettlement(row) {
        this.temp = Object.assign({}, row) // copy obj
        activitySettle(row.id).then(response => {
          this.getList()
          this.$notify({
            title: '成功',
            message: '结算成功',
            type: 'success',
            duration: 2000
          })
        })
      },
      resetTemp() {
        this.temp = {
          beginTime: undefined,
          duration: 0,
          courtName: undefined,
          chargeTotal: 0,
          courtNums: 0,
          remark: ''
        }
      },
      showCreateDialog() {
        this.resetTemp()
        this.dialogFormVisible = true
        this.fetchCourtList()
      },
      createActivity() {
        const param = {
          'courts': [{
            'beginTime': formatDateYYMMDDHHMM(new Date(this.temp.beginTime)),
            'duration': this.temp.duration,
            'courtNums': this.temp.courtNums,
            'courtID': this.temp.courtID
          }],
          'chargeTotal': this.temp.chargeTotal,
          'remark': this.temp.remark
        }
        create_activity(param).then(response => {
          this.dialogFormVisible = false
          this.getList()
          this.$notify({
            title: '成功',
            message: '发布活动成功',
            type: 'success',
            duration: 2000
          })
          // this.joinForm = response.data
        }).catch(err => {
          console.log(err)
        })
      },
      fetchCourtList() {
        fetchCourtList().then(response => {
          console.log(response.data)
          if (response.data && response.data.data) {
            this.courts = response.data.data
            this.activity_court_options = []
            for (const court of this.courts) {
              this.activity_court_options.push(
                { key: court.id, display_name: court.name })
            }
            console.log(this.activity_court_options)
          }
        }).catch(err => {
          console.log(err)
        })
      },
      cancelActivity(row) {

      }
    }
  }
</script>
