<script lang="ts">
  import { fade } from 'svelte/transition';
  import { ETooltipBehavior } from '$lib/shared/enums/tooltip-behavior.enum'

  // Props
  export let text: string = '';
  export let classes: string = 'relative inline-block';
  export let type: ETooltipBehavior = ETooltipBehavior.None;
  
  
  // Functions
  let visible = false;
  const show = () => visible = true;
  const hide = () => visible = false;
  const toggle = () => visible = !visible;
  
</script>

<!-- svelte-ignore a11y_no_interactive_element_to_noninteractive_role -->
<button 
  class={ classes } 

  on:click|stopPropagation={() => {
    if ((type & ETooltipBehavior.Click) === ETooltipBehavior.Click) toggle();
  }}
  on:focusout={hide}
  role="tooltip"
  aria-label={text}
>
  <slot/>  

  {#if visible}
    <div
      class="absolute bottom-full 
      left-1/2 -translate-x-1/2 
      mb-2 w-max max-w-xs p-2 rounded-md
      bg-black text-white text-sm shadow-lg z-10"
      transition:fade={{ duration: 200 }}
    >
      {text}
    </div>
  {/if}
  </button>