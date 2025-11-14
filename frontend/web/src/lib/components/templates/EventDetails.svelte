<script lang="ts">
	import type { GetEventDetailsResponse } from '$lib/api/queries/get-event-details/get-event-details.interface';

	export let eventData: GetEventDetailsResponse;
</script>

<main class="flex flex-col w-full gap-5 px-10">
    <div class="flex w-full items-start justify-between">
        <div class="flex flex-col flex-1 gap-8">
            <h1 class="h3">Detalhes</h1>

        </div>
        <div class="flex flex-col flex-1 gap-7">
            <h1 class="h3">Jogos</h1>
        </div>
    </div>
</main>