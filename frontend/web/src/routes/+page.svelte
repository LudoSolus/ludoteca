<script lang="ts">
  import HistoryCard from "$lib/components/molecules/HistoryCard.svelte";

  const historico: {
    title: string;
    timePlayed: string;
    date: string;
    type: "escape" | "boardgame" | "rpg";
    system?: string;
    sessions?: number;
  }[] = [
    {
      title: "Fuja da sala",
      timePlayed: "92 min",
      date: "22/10",
      type: "escape"
    },
    {
      title: "WAR",
      timePlayed: "92 min",
      date: "22/10",
      type: "boardgame"
    },
    {
      title: "A era glacial de Ragnar",
      timePlayed: "—",
      date: "—",
      type: "rpg",
      system: "DED",
      sessions: 5
    }
  ];

  const filtrarPorTipo = (tipo: "escape" | "boardgame" | "rpg") =>
    historico.filter((item) => item.type === tipo);
</script>

<main class="min-h-screen bg-[#fffcee] p-10 space-y-12">
  <section class="flex flex-col items-center gap-6">
    {#each filtrarPorTipo("escape") as item}
      <HistoryCard
        title={item.title}
        timePlayed={item.timePlayed}
        date={item.date}
        type={item.type}
        system={item.system}
        sessions={item.sessions}
      />
    {/each}
  </section>

  <section class="flex flex-col items-center gap-6">
    {#each filtrarPorTipo("boardgame") as item}
      <HistoryCard
        title={item.title}
        timePlayed={item.timePlayed}
        date={item.date}
        type={item.type}
        system={item.system}
        sessions={item.sessions}
      />
    {/each}
  </section>

  <section class="flex flex-col items-center gap-6">
    {#each filtrarPorTipo("rpg") as item}
      <HistoryCard
        title={item.title}
        timePlayed={item.timePlayed}
        date={item.date}
        type={item.type}
        system={item.system}
        sessions={item.sessions}
      />
    {/each}
  </section>
</main>
