<script lang="ts">
	import { onMount } from 'svelte';

	export let size: number;
	export let onComplete: (code: string) => void;

	let values: string[] = [];

	onMount(() => {
		values = Array(size).fill('');
	});

	function handleInput(e: Event, index: number) {
		const target = e.target as HTMLInputElement;
		const val = target.value.replace(/\D/g, '');

		values[index] = val;

		if (val && index < values.length - 1) {
			const next = document.getElementById(`otp-${index + 1}`);
			next?.focus();
		}

		if (values.every((v) => v !== '')) {
			onComplete(values.join(''));
		}
	}

	function handleKeydown(e: KeyboardEvent, index: number) {
		const target = e.target as HTMLInputElement;

		if (e.key === 'Backspace' && !target.value && index > 0) {
			const prev = document.getElementById(`otp-${index - 1}`);
			prev?.focus();
		}
	}
</script>

<div class="inter flex flex-wrap justify-center gap-2 sm:gap-3">
	{#each values as value, i}
		<input
			id={'otp-' + i}
			class="otp-input"
			type="text"
			maxlength="1"
			bind:value={values[i]}
			on:input={(e) => handleInput(e, i)}
			on:keydown={(e) => handleKeydown(e, i)}
		/>
	{/each}
</div>

<style>
	.otp-input {
		width: 40px;
		height: 50px;
		border-radius: 8px;
		border: 1px solid #2b2b2b;
		background: white;
		text-align: center;
		font-size: 1.8rem;
		font-weight: 600;
		padding: 0.4rem 0.5rem;
	}

	@media (min-width: 375px) {
		.otp-input {
			width: 45px;
			height: 58px;
			font-size: 2rem;
			padding: 0.5rem 0.75rem;
		}
	}
</style>
