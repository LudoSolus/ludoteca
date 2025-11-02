<script lang="ts">
  import Fa from "svelte-fa";
  import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
  import type { IconDefinition } from "@fortawesome/free-solid-svg-icons";
  import Button from "$lib/components/atoms/Button.svelte";

  export let title: string;
  export let dateStart: string;
  export let timeStart: string;
  export let dateEnd: string;
  export let timeEnd: string;
  export let activities: { icon: IconDefinition; text: string }[] = [];
  export let address: string[] = [];
  export let image: string;
</script>

<div class="bg-[#fff6c4] border border-black rounded-[10px] p-6 shadow-[0px_4px_4px_rgba(0,0,0,0.25)] flex flex-col sm:flex-row justify-between items-start w-full max-w-3xl text-black font-serif">
  
  <div class="flex-1 space-y-4">
    <h2 class="text-3xl font-bold font-playfair">{title}</h2>

    <div class="space-y-2 mt-2">
      {#each activities as activity}
        <div class="flex items-center gap-2 text-lg font-bold">
          <Fa icon={activity.icon} class="text-black w-5 h-5" />
          <span>{activity.text}</span>
        </div>
      {/each}
    </div>

    <div class="mt-4 text-base leading-tight font-bold">
      <div class="flex items-start gap-2">
        <Fa icon={faLocationDot} class="text-black w-5 h-5 mt-1" />
        <div class="space-y-1">
          {#each address as line}
            <p>{line}</p>
          {/each}
        </div>
      </div>
    </div>

    <div class="mt-6">
      <Button text="Detalhes →" onClick={() => {}} />
    </div>
  </div>

  
  <div class="flex-shrink-0 mt-6 sm:mt-0 sm:ml-8 flex flex-col items-end gap-3">
    <div class="text-right text-sm font-bold text-gray-800">
      <p>{dateStart} - {dateEnd}</p>
      <p>{timeStart} - {timeEnd}</p>
    </div>

    <img src={image} alt="Imagem do evento" class="w-48 h-48 object-contain rounded-md" />
  </div>
</div>
