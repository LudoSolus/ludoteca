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

<div class="event-card flex h-62 w-120 flex-col items-start gap-3 pt-3">
	<section class="flex w-full items-center justify-between px-4">
		<div class="flex gap-6">
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
			<p class="text-[18px]">{formatLongDate(startDate)}</p>
		</div>
	</section>
	<section class="event-details-section flex h-full w-full flex-col justify-between px-4 pt-3 pb-8">
		<h4 class="inknut text-2xl">{name}</h4>
		<div class="flex items-end justify-between">
			<div class="flex items-center gap-3">
				<Icon icon={faLocationDot} size={28} />
				<div>
					<p>{address.street}, n° {address.number}</p>
					<p>{address.city}, {address.state}</p>
				</div>
			</div>
			<div class="flex gap-3">
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
					width="140px"
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
		border-radius: 16px;
		background: var(--card-background-color);
	}
</style>
