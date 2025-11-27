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
	import escapeRoomKey from '$lib/assets/key-svgrepo-com.svg';
	import { device } from '$lib/shared/hooks/useDevice';

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
	export let onCLickButton: () => void
</script>

<div
	class="event-card flex w-75 min-w-75 flex-col items-start justify-between gap-6 p-4 sm:w-120 sm:min-w-120 sm:p-6"
>
	<div class="flex w-full items-start justify-between gap-2">
		<h4 class="h-14 max-h-14 overflow-hidden text-lg font-bold sm:text-xl">
			{name}
		</h4>
		<div class="inter flex items-center gap-1 font-medium sm:gap-3">
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
	</div>
	<div class="flex w-full flex-col gap-4 sm:flex-row">
		<div class="flex flex-1 flex-col gap-4">
			<div class="space-y-2">
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
				<div class="flex gap-3">
					<Fa icon={faLocationDot} class="mt-2 text-2xl" />
					<div class="text-sm">
						<p>{address.street}, n° {address.number}</p>
						<p>Bairro: {address.neighborhood}</p>
						<p>{address.city}, {address.state}</p>
						<p class="inter">{formatCEP(address.zipCode)}</p>
					</div>
				</div>
			</div>
		</div>

		<div class="flex-1 flex shrink-0 flex-col items-end justify-between gap-3 sm:h-full">
			<img
				src={defaultEventImg}
				alt="Imagem do evento"
				class=" hidden h-35 w-35 rounded-md object-contain sm:flex"
			/>

			<Button
				text="Detalhes"
				rightIcon={faArrowRight}
				width={$device == 'mobile' ? '100%' : undefined}
				onClick={onCLickButton}
			/>
		</div>
	</div>
</div>

<style>
	.event-card {
		border-radius: 10px;
		border: 1px solid black;
		background: var(--card-background-color);
		box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
	}
</style>
