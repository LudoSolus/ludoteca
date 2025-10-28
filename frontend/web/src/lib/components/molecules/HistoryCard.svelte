<script lang="ts">
  import { FontAwesomeIcon } from "@fortawesome/svelte-fontawesome";
  import {
    faClock,
    faCalendarDays,
    faKey,
    faDiceD20,
    faGamepad
  } from "@fortawesome/free-solid-svg-icons";

  export let title: string = "Título da atividade";
  export let type: "escape" | "rpg" | "boardgame" = "rpg";
  export let timePlayed: string = "";
  export let date: string = "";
  export let system: string = "";
  export let sessions: number = 0;

  // Mapeamento por tipo
  $: config = {
    escape: { color: "#6f1272", icon: faKey },
    rpg: { color: "#6a77c9", icon: faDiceD20 },
    boardgame: { color: "#a7a05c", icon: faGamepad },
  }[type];
</script>

<div class="flex flex-col sm:flex-row items-stretch w-full max-w-xl rounded-xl overflow-hidden shadow-md border border-black bg-white">
  <!-- Conteúdo principal -->
  <div class="flex flex-col justify-center gap-3 p-4 flex-1">
    <h3 class="text-xl font-bold text-black">{title}</h3>

    {#if timePlayed}
      <div class="flex items-center gap-2 text-sm text-[#404040] font-medium">
        <FontAwesomeIcon icon={faClock} class="w-5 h-5 text-[#404040]" />
        <span>{timePlayed}</span>
      </div>
    {/if}

    {#if date}
      <div class="flex items-center gap-2 text-sm text-[#404040] font-medium">
        <FontAwesomeIcon icon={faCalendarDays} class="w-5 h-5 text-[#404040]" />
        <span>{date}</span>
      </div>
    {/if}

    {#if system}
      <div class="text-sm text-[#404040] font-medium">
        <span class="font-bold">Sistema:</span> {system}
      </div>
    {/if}

    {#if sessions > 0}
      <div class="text-sm text-[#404040] font-medium">
        <span class="font-bold">Sessões:</span> {sessions}
      </div>
    {/if}
  </div>

  <!-- Ícone lateral -->
  <div
    class="flex items-center justify-center flex-shrink-0 min-w-[100px] aspect-square"
    style="background-color: {config.color};"
  >
    <FontAwesomeIcon icon={config.icon} class="text-white w-16 h-16" />
  </div>
</div>

