<script lang="ts">
	export let color: string = '#FFF';
	export let duration: string = '0.75s';
	export let size: string = '60px';
	export let weight: string = '3px';
	export let pause: boolean = false;
</script>

<div
	class="circle"
	class:pause-animation={pause}
	style="--size: {size}; --color: {color}; --duration: {duration}; --weight: {weight};"
></div>

<style>
	.circle {
		height: var(--size);
		width: var(--size);
		border-color: var(--color) transparent var(--color) var(--color);
		border-width: var(--weight);
		border-style: solid;
		border-image: initial;
		border-radius: 50%;
		animation: var(--duration) linear 0s infinite normal none running rotate;
	}
	.pause-animation {
		animation-play-state: paused;
	}
	@keyframes rotate {
		0% {
			transform: rotate(0);
		}
		100% {
			transform: rotate(360deg);
		}
	}
</style>
