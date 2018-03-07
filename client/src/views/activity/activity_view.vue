<template>
  <div class="app-container calendar-list-container">
    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">

      <el-table-column width="150px" align="center" :label="$t('activity_table.begin_time')">
        <template slot-scope="scope">
          <span>{{scope.row.beginTime}}</span>
        </template>
      </el-table-column>

       <el-table-column width="150px" align="center" :label="$t('activity_table.duration')">
        <template slot-scope="scope">
          <span>{{scope.row.duration}}</span>
        </template>
      </el-table-column>


      <el-table-column width="150px" :label="$t('activity_table.court_name')">
        <template slot-scope="scope">
          <span>{{scope.row.courtName}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('activity_table.chargeTotal')">
        <template slot-scope="scope">
          <span>{{scope.row.chargeTotal}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150px" :label="$t('activity_table.participate_total')">
        <template slot-scope="scope">
          <span>{{scope.row.participateTotal}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" :label="$t('activity_table.participate_users')">
        <template slot-scope="scope">
          <span>{{scope.row.participateUsers}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" :label="$t('activity_table.remark')">
        <template slot-scope="scope">
          <span>{{scope.row.remark}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" :label="$t('activity_table.activityState')">
        <template slot-scope="scope">
          <span>{{scope.row.state? "已结束":"报名中"}}</span>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import { fetchActivityList } from '@/api/court'
import { formatDateYYMMDDHHMM } from '@/utils/common'
export default {
  name: 'activityTable',
  data() {
    return {
      tableKey: 2,
      list: null,
      total: null,
      listLoading: true
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
          this.list.push(activityItem)
        }

        // this.total = response.data.total
        this.listLoading = false
      })
    }
  }
}
</script>
