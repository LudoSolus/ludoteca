<script lang="ts">
  import keyIcon from "$lib/assets/key-svgrepo-com.svg";
  import boardgameIcon from "$lib/assets/map_adventure.svg";
  import diceIcon from "$lib/assets/dice-twenty-faces-one-svgrepo-com.svg";

  import { faClock, faCalendar } from "@fortawesome/free-solid-svg-icons";
  import FA from "svelte-fa";

  export let title: string = "Título da atividade";
  export let type: "escape" | "rpg" | "boardgame" = "rpg";
  export let timePlayed: string = "";
  export let date: string = "";
  export let system: string = "";
  export let sessions: number = 0;

  $: config = {
    escape: { image: keyIcon, bg: "#6f1272" },
    rpg: { image: diceIcon, bg: "#6a77c9" },
    boardgame: { image: boardgameIcon, bg: "#a7a05c" }
  }[type];
</script>

<div class="flex w-full max-w-[613px] h-[105px] rounded-[10px] overflow-hidden shadow-md border border-black bg-white">
  <div class="flex flex-col justify-center gap-[10px] px-[14px] py-[12px] flex-1">
    <h3 class="text-[24px] leading-[30px] font-inknut font-light text-black">
      {title}
    </h3>

    {#if timePlayed}
      <div class="flex items-center gap-[6px] text-[18px] leading-[22px] font-inknut font-normal text-[#2B2B2B]">
        <FA icon={faClock} class="w-[14px] h-[14px]" />
        <span>{timePlayed}</span>
      </div>
    {/if}

    {#if date}
      <div class="flex items-center gap-[6px] text-[18px] leading-[22px] font-inknut font-normal text-[#2B2B2B]">
        <FA icon={faCalendar} class="w-[14px] h-[14px]" />
        <span>{date}</span>
      </div>
    {/if}

    {#if system}
      <div class="text-[18px] leading-[22px] font-inknut font-normal text-[#2B2B2B]">
        <span class="font-bold">Sistema:</span> {system}
      </div>
    {/if}

    {#if sessions > 0}
      <div class="text-[18px] leading-[22px] font-inknut font-normal text-[#2B2B2B]">
        <span class="font-bold">Sessões:</span> {sessions}
      </div>
    {/if}
  </div>

  <div class="flex items-center justify-center w-[109px] h-[105px]" style="background-color: {config.bg};">
    <img src={config.image} alt="Ícone" class="w-[56px] h-[56px] filter invert brightness-0" />
  </div>
</div>
