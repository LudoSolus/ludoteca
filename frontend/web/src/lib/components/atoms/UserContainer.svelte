<script lang="ts">
  import { faCrown, faUser } from '@fortawesome/free-solid-svg-icons';
  import Fa from 'svelte-fa';

  export let email: string;
  export let ageGroup: string;
  export let isRpgMaster: boolean = false;
  export let onClick: () => void = () => {};
</script>

<button 
  class="flex items-center justify-between min-h-12 min-[590px]:min-h-12 h-12 min-[590px]:h-12 w-full gap-2" 
  on:click={onClick}
>
  <div
    class="type-icon-box flex items-center justify-center justify-self-start" 
    style="background-color: {isRpgMaster ? '#022780' : '#992C2C'};"
  >
    <Fa icon={isRpgMaster ? faCrown : faUser} color="#fff" size="2x" />
  </div>

  <div class="grid grid-cols-[1fr_auto] items-center w-full pl-2 pr-4 gap-2">
    
    <div class="min-w-0 text-start">
      <p class="email-text">
        {email}
      </p>
    </div>
  
    

		<div class="flex gap-2 items-center justify-self-end w-26 px-2 text-start whitespace-nowrap ">
			<p class="text-sm ">
				{ageGroup}
			</p>
		</div>
  </div>

</button>

<style>
  button {
    background-color: white;
    border: 2px solid #000;
    border-top-left-radius: 10px;
    border-bottom-left-radius: 10px;
    border-top-right-radius: 50px;
    border-bottom-right-radius: 50px;
    cursor: pointer;
  }

  .type-icon-box {
    width: 60px;
    min-width: 60px;
    height: 100%;
  }

  .email-text {
    width: 100%; 
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>