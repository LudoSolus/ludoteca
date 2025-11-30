<script lang="ts">
  import HistoryCard from '$lib/components/molecules/HistoryCard.svelte';

  type HistoryType = "rpg" | "boardgame" | "escape";

  interface HistoryItem {
    title: string;
    type: HistoryType;
    timePlayed?: string;
    date?: string;
    system?: string;
    sessions?: number;
  }

  const historyItems: HistoryItem[] = [
    { title: "A era glacial de ragnar", type: "rpg", system: "DezD", sessions: 5 },
    { title: "WAR", type: "boardgame", timePlayed: "92 min", date: "22/10" },
    { title: "Fuja da sala", type: "escape", timePlayed: "92 min", date: "22/10" },
    { title: "Banco imobiliário", type: "boardgame", timePlayed: "92 min", date: "22/10" },
  ];
</script>

<section class="history-screen min-h-screen relative">
  
  <h1 class="absolute top-32 left-10 text-5xl font-inknut text-black z-10">Tempo Jogado</h1>

  
  <aside
    class="absolute right-0 top-32
           w-[800px]
           h-[85vh]
           bg-[#fff2a8]
           border border-black 
           rounded-xl
           shadow-[0_6px_0_rgba(0,0,0,0.3)]
           px-12 py-10
           flex flex-col gap-8
           overflow-y-auto"
  >
    
    <h2 class="text-4xl font-inknut mb-4 ml-2 self-start">Histórico</h2>

    <div class="flex flex-col w-full max-w-[800px] mx-auto gap-8">
      {#each historyItems as item}
        <HistoryCard
          title={item.title}
          type={item.type}
          timePlayed={item.timePlayed}
          date={item.date}
          system={item.system}
          sessions={item.sessions}
        />
      {/each}
    </div>
  </aside>
</section>

<style>
  .history-screen {
    background: #fffcee;
  }
</style>
