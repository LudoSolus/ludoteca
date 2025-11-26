<script lang="ts">
	import { faArrowLeft, faEdit, faTrash } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';
	import Button from '../atoms/Button.svelte';
	import { device } from '$lib/shared/hooks/useDevice';
	import IconButton from '../atoms/IconButton.svelte';

	export let title: string;
	export let description: string;
	export let onEdit: (() => void) | null = null;
	export let onDelete: (() => void) | null = null;

	function goBack() {
		window.history.back();
	}
</script>

<div class="flex w-full flex-col gap-4 py-2">
	<div class="relative flex w-full items-end justify-between">
		<div class="flex min-w-0 flex-1 items-center gap-3">
			<button on:click={goBack} class="flex cursor-pointer items-center gap-2">
				<Fa icon={faArrowLeft} />
				<p class="hidden font-medium sm:flex">Voltar</p>
			</button>
			<div class="hidden min-h-11 border-l-2 border-black sm:flex"></div>
			<div class="flex min-w-0 flex-1 flex-col gap-2">
				<p class="truncate text-lg font-semibold">{title}</p>
				<p class="truncate text-xs">{description}</p>
			</div>
		</div>

		{#if onEdit || onDelete}
			<div class="flex flex-none shrink-0 justify-end gap-6 px-5">
				{#if $device == 'desktop'}
					{#if onEdit}
						<Button text="Editar" leftIcon={faEdit} onClick={onEdit} />
					{/if}
					{#if onDelete}
						<Button text="Excluir" leftIcon={faTrash} onClick={onDelete} />
					{/if}
				{:else}
					{#if onEdit}
						<IconButton icon={faEdit} onClick={onEdit} width="40px" height="40px" />
					{/if}
					{#if onDelete}
						<IconButton icon={faTrash} onClick={onDelete} width="40px" height="40px" />
					{/if}
				{/if}
			</div>
		{/if}
	</div>
	<div class="w-full border-b-2 border-black"></div>
</div>
