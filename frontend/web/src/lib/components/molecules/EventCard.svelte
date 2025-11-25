<script lang="ts">
	import Fa from 'svelte-fa';
	import {
		faArrowRight,
		faChessBoard,
		faDice,
		faLocationDot
	} from '@fortawesome/free-solid-svg-icons';
	import Button from '$lib/components/atoms/Button.svelte';
	import { formatDate } from '$lib/shared/helpers/format-date';
	import { formatTime } from '$lib/shared/helpers/format-time';
	import defaultEventImg from '$lib/assets/event-boardgame.png';
	import EventActivity from '../atoms/EventActivity.svelte';
	import { formatCEP } from '$lib/shared/helpers/format-cep';
  import escapeRoomKey from "$lib/assets/key-svgrepo-com.svg"

	interface EventAddressData {
		street: string;
		number: string;
		neighborhood: string;
		city: string;
		state: string;
		zipCode: string;
	}

	export let name: string;
	export let startDate: Date;
	export let endDate: Date;
	export let hasBoardGame: boolean;
	export let hasRpg: boolean;
	export let hasEscapeRoom: boolean;
	export let address: EventAddressData;
</script>

<div
	class="flex w-140 flex-col items-start
         justify-between gap-6 rounded-[10px] border
         border-black bg-(--card-background-color) p-6 shadow-[0px_4px_4px_rgba(0,0,0,0.25)] sm:flex-row"
>
	<div class="flex flex-1 flex-col gap-4">
		<h4 class="h-16 max-h-16 overflow-hidden text-2xl font-bold">
			{name}
		</h4>

		<div class="mt-2 space-y-2">
			{#if hasBoardGame}
				<EventActivity title="Jogos de Tabuleiro" icon={faChessBoard} />
			{/if}
			{#if hasRpg}
				<EventActivity title="RPG's" icon={faDice} />
			{/if}
			{#if hasEscapeRoom}
				<EventActivity title="Escape Room" icon={escapeRoomKey} />
			{/if}
		</div>

		<div class="font-medium">
			<div class="flex gap-4">
				<Fa icon={faLocationDot} class="mt-2 text-[28px]" />
				<div class="text-[18px]">
					<p>{address.street}, n° {address.number}</p>
					<p>Bairro: {address.neighborhood}</p>
					<p>{address.city}, {address.state}</p>
					<p class="inter">{formatCEP(address.zipCode)}</p>
				</div>
			</div>
		</div>
	</div>

	<div class="flex h-full shrink-0 flex-col items-end justify-between gap-3">
		<div class="inter flex items-center gap-3 font-medium">
			<div class="felx flex-col items-center">
				<p class="text-center">{formatDate(startDate)}</p>
				<p class="text-center">{formatTime(startDate)}</p>
			</div>
			<p>-</p>
			<div class="felx flex-col items-center">
				<p class="text-center">{formatDate(endDate)}</p>
				<p class="text-center">{formatTime(endDate)}</p>
			</div>
		</div>

		<img src={defaultEventImg} alt="Imagem do evento" class="h-35 w-35 rounded-md object-contain" />

		<div class="mt-6">
			<Button text="Detalhes" rightIcon={faArrowRight} onClick={() => {}} />
		</div>
	</div>
</div>
