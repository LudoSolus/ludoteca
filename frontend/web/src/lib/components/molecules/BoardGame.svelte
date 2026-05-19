<script lang="ts">
  import { faChessBoard, faRightLeft, faUserGroup } from '@fortawesome/free-solid-svg-icons';
  import { ETooltipBehavior } from '$lib/shared/enums/tooltip-behavior.enum';
  import { ECategory } from '$lib/shared/enums/category.enum';
  import { categoryIconMap } from '$lib/shared/helpers/category-icon.map';
  import Tooltip from '$lib/components/atoms/Tooltip.svelte';
  import Icon from '$lib/components/atoms/icons/Icon.svelte';
	import { EUserRole } from '$lib/shared/enums/user-role.enum';

  // Props
  export let title: string;
  export let category: ECategory;
  export let minParticipants: number | undefined = undefined;
  export let maxParticipants: number | undefined = undefined;
  export let userType: EUserRole = EUserRole.USER;
  
  export let isActivate: boolean = false;

  // Events
  export let onClick: () => void = () => {alert("Abrir Página do Jogo!")};
  export let onAdminClick: () => void = () => {alert("Ação Admin!")};

  // Reatividade
  let clientTitleWidth: number = 0;
  let clientCategoryWidth: number = 0;
  let titleRef: HTMLParagraphElement;
  let categoryRef: HTMLParagraphElement;
  let isTitleTruncate: boolean = false;
  let isCategoryTruncate: boolean = false;

  $: {
    if (titleRef && title) {
      isTitleTruncate = titleRef.scrollWidth > clientTitleWidth;
    }
  }
  $: {
    if (categoryRef && category) {
      isCategoryTruncate = categoryRef.scrollWidth > clientCategoryWidth;
    }
  }

  // Logic Displaying Participants
  $: participantsText = [minParticipants, maxParticipants].filter(Boolean).join('-'); 

  // REACTIVE CLASSES
  $: isAdmin = userType === EUserRole.ADMIN;

  // Container Classes
  const containerBaseClasses = 'flex items-center border-2 border-solid border-black rounded-r-[2rem] rounded-l-md min-h-12 min-[480px]:min-h-[10vw] min-[590px]:min-h-14 h-12 min-[480px]:h-[10vw] min-[590px]:h-14 cursor-pointer relative';
  const containerWidthUser = 'w-[94vw] min-[590px]:w-136';
  const containerWidthAdmin = 'w-[94vw] min-[590px]:w-154';
  $: containerClasses = `${containerBaseClasses} ${isAdmin ? containerWidthAdmin : containerWidthUser}`;

  // Div End Classes
  const divEndClasses = 'justify-self-end flex itens-between';
  const divWidthUser = '';
  const divWidthAdmin = 'w-38 max-[590px]:w-30';
  $: endClasses = `${divEndClasses} ${isAdmin ? divWidthAdmin : divWidthUser}`;

  // Div End Classes
  const categoryBaseClasses = 'flex items-center';
  const categoryWidthUser = "w-56 max-[590px]:w-[38vw] max-[460px]:w-[36vw] max-[430px]:w-0 pl-22 max-[590px]:pl-16 max-[510px]:pl-[13vw] max-[430px]:pl-[16vw] max-[380px]:pl-[12vw] max-[350px]:pl-[8vw]";
  const categoryWidthAdmin = "w-56 max-[590px]:w-[32vw] max-[490px]:w-0 pl-16 max-[590px]:pl-16 max-[510px]:pl-[13vw] max-[430px]:pl-[16vw] max-[380px]:pl-[12vw] max-[350px]:pl-[8vw]";
  $: categoryClasses = `${categoryBaseClasses} ${isAdmin ? categoryWidthAdmin : categoryWidthUser}`;

</script>

<div
  class={containerClasses}
  on:click={onClick}
  on:keydown={
    (event: KeyboardEvent) => {
      if (event.key === 'Enter' || event.key === ' ') 
      {event.preventDefault(); if (onClick) onClick()}
    }
  }
  role="button"
  tabindex="0"
>
  <div
    class="flex items-center justify-center w-16 min-h-full rounded-[3px] box-size:border-box"
    style="background-color: {isActivate ? '#2C9959' : '#992C2C'};"
  >
    <Icon icon={faChessBoard} color="#fff" size={2} />
  </div>

  <div class={'grid items-center grid-cols-3 w-full'}>
    
    <div class="
      flex items-center justify-self-start 
      w-60 max-[590px]:w-[34vw] max-[460px]:w-[36vw] 
      max-[360px]:w-[32vw]
      "
    >
      {#if isTitleTruncate}
        <Tooltip
          text={title}
          classes="relative inline-block w-full"
          type={ETooltipBehavior.Click | ETooltipBehavior.FocusIn | ETooltipBehavior.FocusOut}
        >
          <p bind:clientWidth={clientTitleWidth} bind:this={titleRef} class="inknut truncate text-start text-sm pl-2">
            {title}
          </p>
        </Tooltip>
      {:else}
        <p bind:clientWidth={clientTitleWidth} bind:this={titleRef} class="inknut truncate text-start text-sm pl-2">
          {title}
        </p>
      {/if}
    </div>

    <div class={categoryClasses}>
      {#if isCategoryTruncate}
        <Tooltip
          text={category}
          classes="relative inline-block w-full"
          type={ETooltipBehavior.Click | ETooltipBehavior.FocusIn | ETooltipBehavior.FocusOut}
        >
          <div class="flex items-center">
            <Icon icon={categoryIconMap[category]} color="#000" size={1} classes="shrink-0" />
            <p
              bind:clientWidth={clientCategoryWidth}
              bind:this={categoryRef}
              class="truncate text-start text-sm pl-2"
            >
              {category}
            </p>
          </div>
        </Tooltip>
      {:else}
        <div class="relative inline-block w-full">
          <div class="flex items-center">
            <Icon icon={categoryIconMap[category]} color="#000" size={1} classes="shrink-0" />
            <p
              bind:clientWidth={clientCategoryWidth}
              bind:this={categoryRef}
              class="truncate text-start text-sm pl-2"
            >
              {category}
            </p>
          </div>
        </div>
      {/if}
    </div>

    <div class={endClasses}>
      <div class="flex items-center w-20 pl-2">
        <Icon icon={faUserGroup} color="#000" size={1} />
        <p class="text-center text-sm pl-2">
          {participantsText}
        </p>
      </div>
      
      {#if isAdmin}
        <button
          type="button"
          class="
            absolute top-1/2 -translate-y-1/2 right-3 flex items-center justify-center
            rounded-full bg-[#E3E5FF] 
            w-10 h-10 
            font-bold text-white 
            transition-colors hover:bg-[#E3E5F2]
            cursor-pointer
          "
          on:click|stopPropagation={onAdminClick}
        >
          <Icon icon={faRightLeft} color="#000" size={1} />
        </button>
      {/if}
    </div>


  </div>

</div>