<script lang="ts">
  import RpgCharacter from '$lib/components/atoms/RpgCharacter.svelte';
  import EventCard from "$lib/components/molecules/EventCard.svelte";
  import HistoryCard from "$lib/components/molecules/HistoryCard.svelte";

  import {
    faGamepad,
    faDice,
    faKey,
    faGhost,
    faPuzzlePiece,
    faHeart
  } from "@fortawesome/free-solid-svg-icons";

  // Lista de personagens
  const personagens = [
    { name: "Elza Rainfrost" },
    { name: "Arthos Varyn" }
  ];

  // Lista de eventos
  const eventos = [
    {
      title: "Evento 55",
      dateStart: "20/07",
      timeStart: "08:00",
      dateEnd: "20/07",
      timeEnd: "18:00",
      activities: [
        { icon: faGamepad, text: "Jogos de Tabuleiro" },
        { icon: faDice, text: "RPG’s" },
        { icon: faKey, text: "Escape Room" },
      ],
      address: [
        "Rua dos Expedicionários, nº 56",
        "Bairro Alvorada",
        "Cornélio Procópio, PR",
      ],
      image: "/images/event-boardgame.png",
    },
    {
      title: "Noite do Horror",
      dateStart: "21/07",
      timeStart: "19:00",
      dateEnd: "21/07",
      timeEnd: "23:59",
      activities: [
        { icon: faGhost, text: "RPG de Terror" },
        { icon: faHeart, text: "Card Games" },
        { icon: faPuzzlePiece, text: "Puzzle Zone" },
      ],
      address: [
        "Av. Central, nº 1020",
        "Centro",
        "Cornélio Procópio, PR",
      ],
      image: "/images/event-boardgame.png",
    },
  ];

  // Lista de histórico de atividades
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

  <!-- Seção de personagens -->
  <section class="flex flex-col items-start gap-4">
    {#each personagens as personagem}
      <RpgCharacter name={personagem.name} />
    {/each}
  </section>

  <!-- Seção de eventos -->
  <section class="flex flex-col items-center gap-8">
    {#each eventos as evento}
      <EventCard
        title={evento.title}
        dateStart={evento.dateStart}
        timeStart={evento.timeStart}
        dateEnd={evento.dateEnd}
        timeEnd={evento.timeEnd}
        activities={evento.activities}
        address={evento.address}
        image={evento.image}
      />
    {/each}
  </section>

  <!-- Seção de histórico agrupado por tipo -->
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
