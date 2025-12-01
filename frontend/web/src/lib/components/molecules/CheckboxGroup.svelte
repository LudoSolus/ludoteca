<script context="module" lang="ts">
	export interface ICheckboxOption {
		id: string;
		label: string;
		checked: boolean;
		disabled?: boolean;
	}
</script>
<script lang="ts">
	import CheckboxInput from "../atoms/CheckboxInput.svelte";

  export let groupLabel: string = "";
  export let options: ICheckboxOption[] = [];
  export let orientation: 'vertical' | 'horizontal' = 'vertical';
  export let width: string = '100%';
  export let height: string = 'fit-content';
  export let justify: string = 'start';
</script>

<div 
	class:justify-center={justify === 'center'}
	class:justify-start={justify === 'start'}
	class:justify-end={justify === 'end'}
	class:justify-between={justify === 'between'}
	class="flex flex-col gap-2" 
	style="width: {width}; height: {height};"
>
  {#if groupLabel}
    <span class="font-bold text-black mb-1 block">
      {groupLabel}
    </span>
  {/if}

  <div class:flex-col={orientation === 'vertical'} class:flex-row={orientation === 'horizontal'} class="flex gap-3 flex-wrap">
    
    {#each options as option (option.id)}
      <CheckboxInput 
        id={option.id}
        label={option.label}
        disabled={option.disabled}
        bind:checked={option.checked} 
      />
    {/each}

  </div>
</div>