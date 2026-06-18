<!-- EventCardSkeleton.svelte -->
<div
	class="event-card flex h-62 w-75 items-start gap-2 pl-2 md:w-120 md:flex-col md:gap-3 md:pt-3 md:pl-0"
>
	<section class="flex items-center justify-between md:w-full md:px-4">
		<div class="flex flex-col gap-8 px-2 py-4 md:flex-row md:gap-6 md:p-0">
			<div class="skeleton-pulse h-7 w-7 rounded bg-gray-300"></div>
			<div class="skeleton-pulse h-7 w-7 rounded bg-gray-300"></div>
		</div>
		<div>
			<div class="skeleton-pulse hidden h-5 w-32 rounded bg-gray-300 md:block"></div>
		</div>
	</section>
	<section
		class="event-details-section flex h-full flex-col justify-between rounded-2xl rounded-bl-none p-4 md:w-full md:rounded-bl-2xl md:pt-3 md:pb-8"
	>
		<div class="flex w-full flex-col gap-4">
			<div class="flex w-full justify-end md:hidden">
				<div class="skeleton-pulse h-4 w-20 rounded bg-gray-300"></div>
			</div>
			<div class="skeleton-pulse h-6 w-3/4 rounded bg-gray-300"></div>
		</div>
		<div class="flex items-end justify-between gap-2">
			<div class="hidden items-center gap-3 md:flex">
				<div class="skeleton-pulse h-7 w-7 rounded-full bg-gray-300"></div>
				<div class="flex flex-col gap-2">
					<div class="skeleton-pulse h-4 w-40 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-3.5 w-24 rounded bg-gray-300"></div>
				</div>
			</div>
			<div class="flex h-9 w-full gap-3 md:w-47">
				<div class="skeleton-pulse h-9 w-full rounded bg-gray-300"></div>
			</div>
		</div>
	</section>
</div>

<style>
	.event-card {
		border-radius: 16px;
		background: #e5e7eb; /* tailwind gray-200 */
		box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
	}

	.event-details-section {
		background: #f3f4f6; /* tailwind gray-100 */
	}

	.skeleton-pulse {
		animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
	}

	@keyframes pulse {
		0%, 100% {
			opacity: 1;
		}
		50% {
			opacity: 0.4;
		}
	}
</style>
