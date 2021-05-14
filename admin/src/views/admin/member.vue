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
        <th>手机号</th>
        <th>密码</th>
        <th>昵称</th>
        <th>头像url</th>
        <th>注册时间</th>
<!--        <th>操作</th>-->
      </tr>
      </thead>

      <tbody>
      <tr v-for="member in members">
        <td>{{member.id}}</td>
        <td>{{member.mobile}}</td>
        <td>{{member.password}}</td>
        <td>{{member.name}}</td>
        <td>{{member.photo}}</td>
        <td>{{member.registerTime}}</td>

      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import Pagination from "@/components/pagination";

  export default {
    name: "business-member",
    components: {Pagination},
    data: function () {
      return {
        member: {},
        members: [],
      }
    },
    mounted: function () {
      let _this = this;
      //初试设置每一页大小
      this.$refs.pagination.size = 5;
      //初试展示第一页
      _this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-member-sidebar")
    },
    methods: {

      /**
       * 列表查询
       * @param page
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/member/list', {
          page: page,
          size: _this.$refs.pagination.size,
        }).then((response) => {
          Loading.hide();
          // console.log("查询会员列表结果：", response);
          let resp = response.data;
          _this.members = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        })
      },

      /**
       * 点击【保存】
       * @param page
       */
      save() {
        let _this = this;

        // 保存校验
        if (1 != 1
          || !Validator.require(_this.member.mobile, "手机号")
          || !Validator.length(_this.member.mobile, "手机号", 1, 11)
          || !Validator.require(_this.member.password, "密码")
          || !Validator.length(_this.member.name, "昵称", 1, 50)
          || !Validator.length(_this.member.photo, "头像url", 1, 200)
        ) {
          return;
        }
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/member/save', _this.member).then((response) => {
          Loading.hide();
          // console.log("保存会员列表结果：", response);
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功!");
          }else {
            Toast.warning(resp.message);
          }
        })
      },

      /**
       * 点击删除
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除会员后不可恢复，确认删除？", function () {
          Loading.show();
          _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/member/delete/' + id).then((response) => {
            Loading.hide();
            // console.log("删除会员列表结果：", response);
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功!");
            }
          })
        });

      }
    }
  }
</script>