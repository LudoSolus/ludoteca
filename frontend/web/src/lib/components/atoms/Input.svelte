<script lang="ts">
	import { generateId } from '$lib/helpers/generate-id';
	import { maska } from 'maska/svelte';
	import type { MaskInputOptions } from 'maska';

	type MaskType = 'phone' | 'cpf' | 'ra';

	export let id: string = generateId(5);
	export let type = 'text';
	export let width: string = '100%';
	export let height: string = 'fit-content';
	export let value: string | null = null;
	export let placeholder: string | null = null;
	export let error: string | null = null;
	export let label: string | null = null;
	export let onInput: (value: string) => void;
	export let mask: MaskType | null = null;

	const maskOptions: MaskInputOptions | undefined = mask ? getMaskOptions() : undefined;

	function getMaskOptions() {
		switch (mask) {
			case 'phone':
				return {
					mask: '(##) #####-####',
					eager: false
				};
			case 'cpf':
				return {
					mask: '###.###.###-##',
					eager: false
				};
			case 'ra':
				return {
					mask: '#######',
					eager: false
				};
		}
	}

	function handleInput(event: any) {
		value = event.target.value;
		onInput(value!);
	}
</script>

<div class="flex flex-col" style="width: {width}; height: {height};">
	<label for={id} class="mb-1 font-bold text-black">
		{label}
	</label>

	<input
		{id}
		{type}
		bind:value
		use:maska={maskOptions}
		{placeholder}
		on:input={handleInput}
		class={`w-full rounded-md border px-3 py-2 focus:ring-2 focus:ring-yellow-400 focus:outline-none ${
			error ? 'border-red-500 focus:ring-red-500' : 'border-black'
		}`}
	/>

	{#if error}
		<span class="mt-1 text-xs text-red-500">{error}</span>
	{/if}
</div>
