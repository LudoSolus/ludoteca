<script lang="ts">
  import EventCard from "$lib/components/molecules/EventCard.svelte";

  const eventos = [
    {
      name: "Evento 55",
      startDate: new Date("2025-07-20T08:00:00"),
      endDate: new Date("2025-07-20T18:00:00"),
      hasBoardGame: true,
      hasRpg: true,
      hasEscapeRoom: true,
      address: {
        street: "Rua dos Expedicionários",
        number: "56",
        neighborhood: "Bairro Alvorada",
        city: "Cornélio Procópio",
        state: "PR",
        zipCode: "86300-000"
      }
    },
    {
      name: "Noite do Horror",
      startDate: new Date("2025-07-21T19:00:00"),
      endDate: new Date("2025-07-21T23:59:00"),
      hasBoardGame: false,
      hasRpg: true,
      hasEscapeRoom: false,
      address: {
        street: "Av. Central",
        number: "1020",
        neighborhood: "Centro",
        city: "Cornélio Procópio",
        state: "PR",
        zipCode: "86300-000"
      }
    },
    {
      name: "Festival de Estratégia",
      startDate: new Date("2025-07-22T14:00:00"),
      endDate: new Date("2025-07-22T22:00:00"),
      hasBoardGame: true,
      hasRpg: false,
      hasEscapeRoom: false,
      address: {
        street: "Praça Central",
        number: "200",
        neighborhood: "Jardim Europa",
        city: "Cornélio Procópio",
        state: "PR",
        zipCode: "86300-000"
      }
    }
  ];
</script>

<section>
  <h1 class="text-3xl font-bold mb-6 text-left font-inknut">Próximos Eventos</h1>

  <div class="flex flex-wrap justify-start gap-x-8 gap-y-10 items-stretch">
    {#each eventos as evento}
      <div class="w-[30rem] min-h-[22rem] flex">
        <EventCard
          name={evento.name}
          startDate={evento.startDate}
          endDate={evento.endDate}
          hasBoardGame={evento.hasBoardGame}
          hasRpg={evento.hasRpg}
          hasEscapeRoom={evento.hasEscapeRoom}
          address={evento.address}
        />
      </div>
    {/each}
  </div>
</section>
