<script lang="ts">
	import { generateId } from '$lib/shared/helpers/generate-id';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';
	import { faChevronDown } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';
	// import { onClickOutside } from '$lib/helpers/on-click-outside'; // helper opcional se quiser fechar clicando fora

	export let id: string = generateId(5);
	export let label: string | null = null;
	export let error: string | null = null;
	export let width: string = '100%';
	export let height: string = 'fit-content';
	export let value: string | null = null;
	export let placeholder: string | null = 'Selecione uma opção';
	export let options: SelectInputOption[] = [];
	export let onChange: (value: string) => void;

	let isOpen = false;

	function toggleDropdown() {
		isOpen = !isOpen;
	}

	function selectOption(option: SelectInputOption) {
		value = option.value;
		isOpen = false;
		onChange(value);
	}
</script>

<div class="relative flex flex-col" style="width: {width}; height: {height};">
	{#if label}
		<label for={id} class="mb-1 font-bold text-black">
			{label}
		</label>
	{/if}

	<button
		{id}
		class={`flex cursor-pointer items-center justify-between rounded-md border bg-white px-3 py-2 text-black select-none focus:ring-2 focus:ring-yellow-400 focus:outline-none ${
			error ? 'border-red-500 focus:ring-red-500' : 'border-black'
		}`}
		on:click={toggleDropdown}
	>
		<span class="text-value {value ? '' : 'text-gray-400'}">
			{value ? options.find((o) => o.value === value)?.label : placeholder}
		</span>

		<Fa class={`h-4 w-4 transition-transform ${isOpen ? 'rotate-180' : ''}`} icon={faChevronDown} />
	</button>

	{#if isOpen}
		<div
			class="options-box absolute z-10 flex w-full flex-col items-start rounded-md border border-black bg-white shadow-md"
			style="top: 74px"
		>
			{#each options as option}
				<button
					class="w-full cursor-pointer px-3 py-2 text-start text-black hover:bg-yellow-100 {option.value ===
					value
						? 'selected'
						: ''}"
					on:click={() => selectOption(option)}
				>
					{option.label}
				</button>
			{/each}
		</div>
	{/if}

	{#if error}
		<span class="mt-1 text-xs text-red-500">{error}</span>
	{/if}
</div>

<style>
	.text-value {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		height: 100%;
	}

	.selected {
		background: var(--primary-color);
		font-weight: 600;
	}

	.options-box {
		max-height: 200px;
		overflow-y: auto;
	}

	div::-webkit-scrollbar {
		width: 6px;
	}
	div::-webkit-scrollbar-thumb {
		background: #d1d5db;
		border-radius: 4px;
	}
</style>
