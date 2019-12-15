<template>
  <div>
    Something Else.
    <button @click="testbutton">This might help!!!</button>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import {test} from '../../services/parser'
@Component
export default class WsUpdate extends Vue {
  @Prop() private msg!: string;
  testbutton(){
    test();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
