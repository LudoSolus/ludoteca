<script lang="ts">
	import defaultImg from '$lib/assets/default.png';

	let { children } = $props();
</script>

<div class="screen-box flex h-full gap-10 overflow-hidden">
	<div class="img-box flex h-full w-full items-center justify-center">
		<img src={defaultImg} alt="Imagem padrão" />
	</div>
	<div class="card">
		{@render children?.()}
	</div>
</div>

<style>
	.screen-box {
		overflow-y: scroll;
		align-items: flex-start;
		justify-content: center;
		padding: 20px 0px;
	}

	.img-box {
		display: none;
	}

	img {
		width: 100%;
		max-width: 500px;
		height: auto;
		object-fit: contain;
	}

	.card {
		width: 100%;
		max-width: 450px;
		background: var(--card-background-color);
		border-radius: 0px;
		box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.7);
	}

	@media (min-width: 375px) {
		.card {
			width: 90%;
			border-radius: 20px;
		}
	}

	@media (min-width: 725px) {
		.card {
			width: 90%;
			max-width: 740px;
		}
	}

	@media (min-width: 1000px) {
		.screen-box {
			padding: 0px;
			align-items: center;
			padding-inline-start: calc(var(--spacing) * 10);
			justify-content: space-between;
		}

		.img-box {
			display: flex;
		}

		.card {
			width: 100vw;
			height: 100%;
			border-top-right-radius: 0px;
			border-top-left-radius: 20px;
			border-bottom-right-radius: 0px;
			border-bottom-left-radius: 20px;
			box-shadow: -2px 0px 4px 0px rgba(0, 0, 0, 0.7);
		}
	}
</style>
