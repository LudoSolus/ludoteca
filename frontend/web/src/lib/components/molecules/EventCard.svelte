<script lang="ts">
	import { faChessBoard, faCopy, faDice, faLocationDot } from '@fortawesome/free-solid-svg-icons';
	import Button from '$lib/components/atoms/Button.svelte';
	import escapeRoomKey from '$lib/assets/key-svgrepo-com.svg';
	import Icon from '../atoms/icons/Icon.svelte';
	import { formatLongDate } from '$lib/shared/helpers/format-long-date';
	import IconButton from '../atoms/IconButton.svelte';

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
	export let hasBoardGames: boolean;
	export let hasRpg: boolean;
	export let hasEscapeRoom: boolean;
	export let address: EventAddressData;
	export let onCLickButton: () => void;
	export let onCopyEvent: null | (() => void) = null;
</script>

<div
	class="event-card flex h-62 w-75 items-start gap-2 pl-2 md:w-120 md:flex-col md:gap-3 md:pt-3 md:pl-0"
>
	<section class="flex items-center justify-between md:w-full md:px-4">
		<div class="flex flex-col gap-8 px-2 py-4 md:flex-row md:gap-6 md:p-0">
			{#if hasBoardGames}
				<Icon icon={faChessBoard} size={28} />
			{/if}
			{#if hasRpg}
				<Icon icon={faDice} size={28} />
			{/if}
			{#if hasEscapeRoom}
				<Icon icon={escapeRoomKey} size={28} />
			{/if}
		</div>
		<div>
			<p class="hidden text-[18px] md:flex">{formatLongDate(startDate, 'long')}</p>
		</div>
	</section>
	<section
		class="event-details-section flex h-full w-full flex-col justify-between rounded-2xl rounded-bl-none p-4 md:rounded-bl-2xl md:pt-3 md:pb-8"
	>
		<div class="flex w-full flex-col gap-4">
			<p class="w-full text-end text-[18px] md:hidden">{formatLongDate(startDate, 'short')}</p>
			<h4 class="inknut text-xl md:text-2xl">{name}</h4>
		</div>
		<div class="flex items-end justify-between gap-2">
			<div class="hidden items-center gap-3 md:flex">
				<Icon icon={faLocationDot} size={28} />
				<div>
					<p>{address.street}, n° {address.number}</p>
					<p>{address.city}, {address.state}</p>
				</div>
			</div>
			<div class="flex h-9 w-full gap-3 md:w-47">
				{#if onCopyEvent}
					<IconButton
						icon={faCopy}
						onClick={onCopyEvent}
						variant="outline"
						width="36px"
						height="36px"
					/>
				{/if}
				<Button
					text="Detalhes"
					onClick={onCLickButton}
					variant="black"
					width="100%"
					height="36px"
				/>
			</div>
		</div>
	</section>
</div>

<style>
	.event-card {
		border-radius: 16px;
		background: var(--primary-color);
		box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.25);
	}

	.event-details-section {
		background: var(--card-background-color);
	}
</style>
