<template>
  <div class="app-container calendar-list-container">
    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">    
      
      <el-table-column width="150px" align="center" :label="$t('activity_table.begin_time')">
        <template slot-scope="scope">
          <span>{{scope.row.begin_time}}</span>
        </template>
      </el-table-column>

       <el-table-column width="150px" align="center" :label="$t('activity_table.duration')">
        <template slot-scope="scope">
          <span>{{scope.row.duration}}</span>
        </template>
      </el-table-column>


      <el-table-column width="150px" :label="$t('activity_table.court_name')">
        <template slot-scope="scope">
          <span>{{scope.row.court_name}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('activity_table.charge')">
        <template slot-scope="scope">
          <span>{{scope.row.charge}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150px" :label="$t('activity_table.participate_total')">
        <template slot-scope="scope">
          <span>{{scope.row.participate_total}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" :label="$t('activity_table.participate_users')">
        <template slot-scope="scope">
          <span>{{scope.row.participate_users}}</span>
        </template>
      </el-table-column>

      

     
    </el-table>  

  </div>
</template>

<script>
import { fetchActivityList } from '@/api/user'

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
        this.list = response.data.items
        // this.total = response.data.total
        this.listLoading = false
      })
    }
  }
}
</script>
