<template>
<div >
  <div class="join-container">
    <el-form class="join-form" autoComplete="off" :model="joinForm"  ref="joinForm" label-position="left" label-width="80px">
      <div class="title-container">
        <h3 v-show="!isRegistry" class="title">{{$t('join.title')}}</h3>
      </div>

      <div class="text">
        <h3 v-show="isRegistry" >您已报名</h3>
      </div>

      <el-form-item  label="时间:" >
        <el-input name="beginTime" type="text" v-model="joinForm.beginTime" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  label="场地:" >
        <el-input name="court" type="text" v-model="joinForm.courtName" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  label="备注:" >
        <el-input name="remark" type="text" v-model="joinForm.remark" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  :label="participate_total_label" >
        <el-input name="join_members" type="textarea" v-bind:autosize="true" v-model="joinForm.participateUsers" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  label="费用:" >
        <el-input name="chargeTotal" type="text" v-model="joinForm.chargeTotal" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  label="人均费用:" >
        <el-input name="average_charge" type="text" v-model="average_charge" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item label="报名人数:">
        <el-input-number v-model="join_nums"  size="mini" :min="1" :max="10" label="描述文字"></el-input-number>
      </el-form-item>

      <el-form-item>
        <el-button  v-show="!isRegistry" type="primary" @click="submitForm()">立即报名
        </el-button>

        <el-button  v-show="isRegistry" @click="unRegistry()">取消报名
        </el-button>

        <el-button type="success" round  @click="showActivity()">创建活动
        </el-button>

      </el-form-item>
    </el-form>
  </div>

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
  import { cancel_activity, create_activity, fetchCourtJoinData, fetchCourtList } from '@/api/court'

  import { formatDateYYMMDDHHMM } from '@/utils/common'

  export default {
    name: 'join',
    data: function() {
      return {
        join_nums: 1,
        isRegistry: false,
        joinForm: {
          id: '',
          beginTime: '2018-01-27',
          courtName: '苏州独墅湖体育馆',
          chargeTotal: '80RMB',
          participateUsers: ['xiejieyi*2', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili', 'xiejieyi', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili'].toString(),
          participate_total: 0
        },

        activity_rules: {
          chargeTotal: [{ required: true, type: 'number', trigger: 'change', max: 1000 }]
        },
        dialogFormVisible: false,

        courts: [],
        activity_court_options: [],
        activity_times_options: [
          { key: 30, display_name: '30分钟' },
          { key: 60, display_name: '60分钟' },
          { key: 90, display_name: '90分钟' },
          { key: 120, display_name: '120分钟' },
          { key: 150, display_name: '150分钟' },
          { key: 180, display_name: '180分钟' }
        ],
        temp: {
          beginTime: undefined,
          duration: 0,
          courtName: undefined,
          chargeTotal: 0,
          courtNums: 0,
          remark: ''
        }
      }
    },
    methods: {
      submitForm() {
        const params = {
          'activity_id': this.joinForm.id,
          'nums': this.join_nums
        }
        this.$store.dispatch('JoinActivity', params).then(() => {
          this.fetchJoinData()
          this.$notify({
            title: '成功',
            message: '报名成功',
            type: 'success',
            duration: 2000
          })
        }).catch(err => {
          console.log(err)
        })
      },
      fetchJoinData: function() {
        fetchCourtJoinData().then(response => {
          console.log(response.data)
          if (response.data && response.data.data) {
            const username = this.$store.getters.username

            // this.joinForm = response.data.data
            // 默认取第一个场地信息（暂时只支持一个场地信息）
            this.joinForm.id = response.data.data.id
            this.joinForm.chargeTotal = response.data.data.chargeTotal
            this.joinForm.remark = response.data.data.remark
            const courts = response.data.data.courts[0]

            const beginTime = courts.beginTime
            const endTime = courts.beginTime + courts.duration * 60 * 1000

            this.joinForm.beginTime = formatDateYYMMDDHHMM(new Date(beginTime))
            this.joinForm.endTime = formatDateYYMMDDHHMM(new Date(endTime))

            this.joinForm.beginTime = this.joinForm.beginTime + ' ~ ' + this.joinForm.endTime
            // 计算人数
            this.joinForm.courtName = courts.courtName

            const users = response.data.data.participates
            let participates_len = 0
            const usernames = []
            for (const user of users) {
              participates_len = user.participateNumbers + participates_len
              if (user.username === username) {
                this.isRegistry = true
              }
              if (user.participateNumbers > 1) {
                user.username = user.username + '*' + user.participateNumbers
              }
              usernames.push(user.username)
            }
            this.joinForm.participate_total = participates_len
            this.joinForm.participateUsers = usernames.join(',')
          }
        }).catch(err => {
          console.log(err)
        })
      },
      unRegistry() {
        const username = this.$store.getters.username

        cancel_activity(username, this.joinForm.id).then(response => {
          console.log(response.data)
          this.fetchJoinData()
          this.isRegistry = false
          this.$notify({
            title: '成功',
            message: '取消报名成功',
            type: 'success',
            duration: 2000
          })
          // this.joinForm = response.data
        }).catch(err => {
          console.log(err)
        })
      },
      showActivity() {
        this.fetchCourtList()
        this.dialogFormVisible = true
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
      }

    },
    created() {
      this.fetchJoinData()
      // window.addEventListener('hashchange', this.afterQRScan)
    },
    destroyed() {
      // window.removeEventListener('hashchange', this.afterQRScan)
    },
    computed: {
      // 计算属性的 getter
      average_charge: function() {
        // `this` 指向 vm 实例
        if (this.joinForm.participate_total === 0) {
          return 0
        }
        return (this.joinForm.chargeTotal / this.joinForm.participate_total).toFixed(2)
      },
      participate_total_label: function() {
        return '已报名(' + this.joinForm.participate_total + ')'
      }
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.join-container {
  position: fixed;
  height: 100%;
  width: 100%;
  overflow: auto;
  .join-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    margin: 50px 25%;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .title-container {
    position: relative;
    .title {
      font-size: 26px;
      font-weight: 400;
      color: #ce8c27;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .text {
    font-size:24px;
    color:#C03639;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
    position:relative;
  }

}
</style>
