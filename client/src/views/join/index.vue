<template>
  <div class="join-container">
    <el-form class="join-form" autoComplete="off" :model="joinForm"  ref="joinForm" label-position="left" label-width="80px">
      <div class="title-container">
        <h3 class="title">{{$t('join.title')}}</h3>
      </div>   

      <el-form-item  label="时间:" >   
        <el-input name="time" type="text" v-model="joinForm.time" readonly=true />
      </el-form-item>

      <el-form-item  label="场地:" >   
        <el-input name="court" type="text" v-model="joinForm.location" readonly=true />
      </el-form-item>

      <el-form-item  :label="participate_total_label" >   
        <el-input name="join_members" type="textarea" autosize=true v-model="joinForm.members" readonly=true />
      </el-form-item>

      <el-form-item  label="费用:" >   
        <el-input name="charge" type="text" v-model="joinForm.charge" readonly=true />
      </el-form-item>
      
      <el-form-item  label="人均费用:" >   
        <el-input name="average_charge" type="text" v-model="average_charge" readonly=true />
      </el-form-item>

      <el-form-item label="报名人数:">
        <el-input-number v-model="join_nums"  size="mini" :min="1" :max="10" label="描述文字"></el-input-number>
      </el-form-item>

      <el-form-item>
        <el-button  type="primary" @click="submitForm()">立即报名
        </el-button>

        <el-button  @click="CancelForm()">取消报名
        </el-button>

        <el-button type="success" round  @click="createActivity()">创建活动
        </el-button>

      </el-form-item>
    </el-form>
  </div>

</template>

<script>
import { fetchCourtJoinData } from '@/api/court'

export default {
  name: 'join',
  data() {
    return {
      join_nums: 1,
      joinForm: {
        time: '2018-01-27',
        location: '苏州独墅湖体育馆',
        charge: '80RMB',
        members: ['xiejieyi*2', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili', 'xiejieyi', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili'],
        participate_total: 0
      }
    }
  },
  methods: {
    submitForm() {
      alert('submit success')
    },
    createActivity() {
      alert('create success')
    },
    fetchJoinData() {
      fetchCourtJoinData().then(response => {
        console.log(response.data)
        this.joinForm = response.data
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

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$light_gray:#eee;

/* reset element-ui css */
.postInfo-container-item {
    float: left;
  }

.join-container {

}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.join-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
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
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

}
</style>
