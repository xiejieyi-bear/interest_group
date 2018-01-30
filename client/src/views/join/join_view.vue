<template>
<div >
  <div class="join-container">
    <el-form class="join-form" autoComplete="off" :model="joinForm"  ref="joinForm" label-position="left" label-width="80px">
      <div class="title-container">
        <h3 class="title">{{$t('join.title')}}</h3>
      </div>   

      <el-form-item  label="时间:" >   
        <el-input name="time" type="text" v-model="joinForm.time" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  label="场地:" >   
        <el-input name="court" type="text" v-model="joinForm.location" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  :label="participate_total_label" >   
        <el-input name="join_members" type="textarea" v-bind:autosize="true" v-model="joinForm.members" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item  label="费用:" >   
        <el-input name="charge" type="text" v-model="joinForm.charge" v-bind:readonly="true" />
      </el-form-item>
      
      <el-form-item  label="人均费用:" >   
        <el-input name="average_charge" type="text" v-model="average_charge" v-bind:readonly="true" />
      </el-form-item>

      <el-form-item label="报名人数:">
        <el-input-number v-model="join_nums"  size="mini" :min="1" :max="10" label="描述文字"></el-input-number>
      </el-form-item>

      <el-form-item>
        <el-button  type="primary" @click="submitForm()">立即报名
        </el-button>

        <el-button  @click="cancelForm()">取消报名
        </el-button>

        <el-button type="success" round  @click="showActivity()">创建活动
        </el-button>

      </el-form-item>
    </el-form>    
  </div>

  <el-dialog title="创建活动" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('activity_table.begin_time')" prop="begin_time">
            <el-date-picker v-model="temp.begin_time" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
        </el-form-item>

        <el-form-item :label="$t('activity_table.duration')" prop="duration">
            <el-select class="filter-item" v-model="temp.duration" placeholder="Please select">
                <el-option v-for="item in  activity_times_options" :key="item.key" :label="item.display_name" :value="item.key">
                </el-option>
            </el-select>
          
        </el-form-item>

        <el-form-item :label="$t('activity_table.court_name')" prop="court_name">
            <el-select class="filter-item" v-model="temp.court_name" placeholder="Please select">
            <el-option v-for="item in  activity_court_options" :key="item.key" :label="item.display_name" :value="item.key">
            </el-option>
            </el-select>
        </el-form-item>

        <el-form-item :label="$t('activity_table.charge')" prop="charge">
          <el-input v-model="temp.charge"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{$t('table.cancel')}}</el-button>
        <el-button type="primary" @click="createActivity">{{$t('table.confirm')}}</el-button>
      </div>
    </el-dialog>
</div>
</template>

<script>
import { fetchCourtJoinData, join_activity, cancel_activity, create_activity } from '@/api/court'

export default {
  name: 'join',
  data() {
    return {
      join_nums: 1,
      joinForm: {
        time: '2018-01-27',
        location: '苏州独墅湖体育馆',
        charge: '80RMB',
        members: ['xiejieyi*2', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili', 'xiejieyi', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili'].toString(),
        participate_total: 0
      },
      dialogFormVisible: false,

      activity_court_options: [
        { key: '0', display_name: '独墅湖体育馆' },
        { key: '1', display_name: '九帮' }
      ],
      activity_times_options: [
        { key: '30', display_name: '30分钟' },
        { key: '60', display_name: '60分钟' },
        { key: '90', display_name: '90分钟' },
        { key: '120', display_name: '120分钟' }
      ],
      temp: {
        begin_time: undefined,
        duration: 0,
        court_name: undefined,
        charge: 0
      }

    }
  },
  methods: {
    submitForm() {
      join_activity().then(response => {
        console.log(response.data)
        alert('submit success')
        // this.joinForm = response.data
      }).catch(err => {
        console.log(err)
      })
    },
    fetchJoinData() {
      fetchCourtJoinData().then(response => {
        console.log(response.data)
        this.joinForm = response.data
      }).catch(err => {
        console.log(err)
      })
    },
    cancelForm() {
      cancel_activity().then(response => {
        console.log(response.data)
        alert('submit success')
        // this.joinForm = response.data
      }).catch(err => {
        console.log(err)
      })
    },
    showActivity() {
      this.dialogFormVisible = true
    },
    createActivity() {
      create_activity().then(response => {
        this.dialogFormVisible = false
        console.log(response.data)
        alert('submit success')
        // this.joinForm = response.data
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
      return (this.joinForm.charge / this.joinForm.members.length).toFixed(2)
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

}
</style>
