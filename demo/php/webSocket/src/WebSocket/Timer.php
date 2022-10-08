<?php

namespace WebSocket;

final class Timer
{
    /**
     * @var int $interval
     */
    private $interval;

    /**
     * @var callable $task
     */
    private $task;

    /**
     * @var float $lastRun
     */
    private $lastRun;

    public function __construct($interval, callable $task)
    {
        $this->interval = $interval;
        $this->task = $task;
        $this->lastRun = 0;
    }

    /**
     * 计时器的方法，用于定时发送心跳包
     *
     * @return void
     */
    public function run()
    {
        $now = round(microtime(true) * 1000);
        if ($now - $this->lastRun < $this->interval) {
            return;
        }

        $this->lastRun = $now;
        call_user_func($this->task);
    }
}