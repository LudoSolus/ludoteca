<script lang="ts">
  import EventCard from "$lib/components/molecules/EventCard.svelte";
  import {
    faGamepad,
    faDice,
    faKey,
    faGhost,
    faPuzzlePiece,
    faHeart
  } from "@fortawesome/free-solid-svg-icons";

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
</script>

<main class="min-h-screen bg-[#fffcee] p-10 space-y-12">
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
</main>