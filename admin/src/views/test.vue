<template>
  <div>
    <h1 v-on:click="present">{{ name }}</h1>
    <p>
      <input v-model="message" placeholder="请输入"/>
      &nbsp;
    </p>
    <p>
      <input v-bind:value="message"/>
    </p>
    <p>
      <button v-on:click="clickMe()">确定</button>
    </p>
    <p>{{count}}</p>
  </div>
</template>

<script>
export default {
  name: "test",
  data: function () {
    return {
      name: "hello, world!",
      message: "",
      count:0,
    }
  },
  methods: {
    present: function () {
      let _this = this;

      Confirm.show("You click me", function () {
        Toast.success("OK");
      });
    },
    clickMe:function () {
      let _this = this;
      _this.count++;
    }
  }
}
</script>