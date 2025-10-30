<script lang="ts">
  import Fa from 'svelte-fa';
  import { faArrowLeftLong, faArrowRight, faTrash, faKey } from '@fortawesome/free-solid-svg-icons';
  import Button from '$lib/components/atoms/Button.svelte';
  export let title: string = '';
</script>

<div
  class="bg-yellow-100 border-2 border-black rounded-xl p-6 shadow-md
         flex flex-col gap-10 w-full max-w-sm transition-all duration-300 hover:shadow-lg"
>
  <div class="flex items-center gap-3">
    <Fa icon={faKey} class="text-3xl text-black" />
    <h2 class="text-2xl font-semibold text-black">{title}</h2>
  </div>

  <Button text={'Detalhes'} onClick={() => {}} rightIcon={faArrowRight} />
</div>
