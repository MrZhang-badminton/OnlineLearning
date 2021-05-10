<template>




  <div>
    <img class="img-responsive" v-bind:src="myUrl">
  </div>
</template>

<script>
export default {
  name: "test",
  data: function () {
    return {
      myUrl:'http://127.0.0.1:9000/file/f/teacher/01nmEVqZ.jpg',
    }
  },
  methods: {
  }
}
</script>