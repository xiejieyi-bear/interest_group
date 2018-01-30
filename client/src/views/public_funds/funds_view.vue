<template>
  <div class="app-container calendar-list-container">
    <el-row class="panel-group" :gutter="40">
      <el-col style='width:250px' class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">余额</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="9280" :duration="3200"></count-to>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">    
      
      <el-table-column width="150px" align="center" :label="$t('funds_table.type')">
        <template slot-scope="scope">
          <span>{{scope.row.type}}</span>
        </template>
      </el-table-column>

       <el-table-column width="150px" align="center" :label="$t('funds_table.time')">
        <template slot-scope="scope">
          <span>{{scope.row.time}}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" :label="$t('funds_table.charge')">
        <template slot-scope="scope">
          <span>{{scope.row.charge}}</span>
        </template>
      </el-table-column>  

      <el-table-column min-width="150px" :label="$t('funds_table.cause')">
        <template slot-scope="scope">
          <span>{{scope.row.cause}}</span>
        </template>
      </el-table-column>

         
    </el-table>  

  </div>
</template>

<script>
import { fetchFundsList } from '@/api/user'
import CountTo from 'vue-count-to'
export default {
  name: 'funds_table',
  components: {
    CountTo
  },
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
      fetchFundsList(this.listQuery).then(response => {
        this.list = response.data.items
        // this.total = response.data.total
        this.listLoading = false
      })
    }
  }
}
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    &:hover {      
      .icon-money {
        background: #f4516c;
      }
    }   
    .icon-money {
      color: #f4516c;
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
