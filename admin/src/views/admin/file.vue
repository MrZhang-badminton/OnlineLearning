<template>
  <div>
    <p>
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="list"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>相对路径</th>
        <th>文件名</th>
        <th>后缀</th>
        <th>大小</th>
        <th>用途</th>
        <!--        <th>已上传分片</th>-->
        <!--        <th>分片大小</th>-->
        <!--        <th>分片总数</th>-->
        <!--        <th>文件标识</th>-->
        <!--        <th>vod</th>-->

      </tr>
      </thead>

      <tbody>
      <tr v-for="file in files">
        <td>{{ file.id }}</td>
        <td>{{ file.path }}</td>
        <td>{{ file.name }}</td>
        <td>{{ file.suffix }}</td>
        <td>{{ file.size|formatFileSize }}</td>
        <td>{{ FILE_USE | optionKV(file.use) }}</td>
        <!--        <td>{{file.shardIndex}}</td>-->
        <!--        <td>{{file.shardSize}}</td>-->
        <!--        <td>{{file.shardTotal}}</td>-->
        <!--        <td>{{file.key}}</td>-->
        <!--        <td>{{file.vod}}</td>-->
      </tr>
      </tbody>
    </table>

  </div>
</template>

<script>
import Pagination from "@/components/pagination";

export default {
  name: "file-file",
  components: {Pagination},
  data: function () {
    return {
      file: {},
      files: [],
      FILE_USE: FILE_USE,
    }
  },
  mounted: function () {
    let _this = this;
    //初试设置每一页大小
    this.$refs.pagination.size = 10;
    //初试展示第一页
    _this.list(1);
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("file-file-sidebar")
  },
  methods: {
    /**
     * 列表查询
     * @param page
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/list', {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        Loading.hide();
        // console.log("查询文件列表结果：", response);
        let resp = response.data;
        _this.files = resp.content.list;
        _this.$refs.pagination.render(page, resp.content.total);
      })
    }
  }
}
</script>