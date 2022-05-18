package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;


public enum DataType {

	/**
	 * 枚举变量
	 */
	TotalIOCount(1),
	/**
	 * 枚举变量
	 */
	ReadIORatio(2),
	/**
	 * 枚举变量
	 */
	WriteIORatio(3),
	/**
	 * 枚举变量
	 */
	CurrentBandWidth(4),
	/**
	 * 枚举变量
	 */
	MaxBandWidth(5),
	/**
	 * 枚举变量
	 */
	CurrentIOPS(6),
	/**
	 * 枚举变量
	 */
	MaxIOPS(7),
	/**
	 * 枚举变量
	 */
	MaxLatency(8),
	/**
	 * 枚举变量
	 */
	AverageLatency(9),
	/**
	 * 枚举变量
	 */
	CacheHit(10),
	/**
	 * 枚举变量
	 */
	LocalWriteCacheRatio(11),
	/**
	 * 枚举变量
	 */
	MirrorWriteCacheRatio(12),
	/**
	 * 枚举变量
	 */
	ReadIOTraffic(13),
	/**
	 * 枚举变量
	 */
	WriteIOTraffic(14),
	/**
	 * 枚举变量
	 */
	MaxPowerConsume(15),
	/**
	 * 枚举变量
	 */
	AvgPowerConsume(16),
	/**
	 * 枚举变量
	 */
	MinPowerConsume(17),
	/**
	 * 枚举变量
	 */
	Usage(18),
	/**
	 * 枚举变量
	 */
	QueueSize(19),
	/**
	 * 枚举变量
	 */
	ResponseTime(20),
	/**
	 * 枚举变量
	 */
	BandWidth(21),
	/**
	 * 枚举变量
	 */
	Throughput(22),
	/**
	 * 读带宽(MB/s)
	 */
	ReadBandWidth(23),
	/**
	 * 读I/O平均大小 （KB）  Average Read I/O Size (KB)
	 */
	AVERAGE_READ_IO_SIZE(24),
	/**
	 * 读IOPS(IO/s)
	 */
	ReadThroughput(25),
	/**
	 * 写带宽(MB/s)
	 */
	WriteBandWidth(26),
	/**
	 * 写I/O平均大小 （KB） Average Write I/O Size (KB)
	 */
	AVERAGE_WRITE_IO_SIZE(27),
	/**
	 * 写IOPS(IO/s)
	 */
	WriteThroughput(28),
	/**
	 * 枚举变量
	 */
	ServiceTime(29),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics512B(30),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics1KB(31),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics2KB(32),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics4KB(33),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics8KB(34),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics16KB(35),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics32KB(36),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics64KB(37),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics128KB(38),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics256KB(39),
	/**
	 * 枚举变量
	 */
	ReadIOStatistics512KB(40),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics512B(41),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics1KB(42),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics2KB(43),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics4KB(44),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics8KB(45),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics16KB(46),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics32KB(47),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics64KB(48),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics128KB(49),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics256KB(50),
	/**
	 * 枚举变量
	 */
	WriteIOStatistics512KB(51),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics512B(52),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics1KB(53),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics2KB(54),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics4KB(55),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics8KB(56),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics16KB(57),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics32KB(58),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics64KB(59),
	/**
	 * 枚举变量
	 */
	CycleWriteIOCnt(60),
	/**
	 * 枚举变量
	 */
	CycleReadIOCnt(61),
	/**
	 * 枚举变量
	 */
	ReadWriteIOStatistics512KB(62),
	/**
	 * 枚举变量
	 */
	WriteIOInPeriod(63),
	/**
	 * 枚举变量
	 */
	ReadIOInPeriod(64),
	/**
	 * 枚举变量
	 */
	RandomSequenceRatio(65),
	/**
	 * 枚举变量
	 */
	Usage2(66),
	/**
	 * 枚举变量
	 */
	ReadBandWidthUsage(67),
	/**
	 * 枚举变量
	 */
	CPUUsage(68),
	/**
	 * 枚举变量
	 */
	MemoryUsage(69),
	/**
	 * 枚举变量
	 */
	SwapUsage(70),
	/**
	 * 枚举变量
	 */
	NetReadTraffic(71),
	/**
	 * 枚举变量
	 */
	NetWriteTraffic(72),
	/**
	 * 枚举变量
	 */
	NetInPackage(73),
	/**
	 * 枚举变量
	 */
	NetOutPackage(74),
	/**
	 * 枚举变量
	 */
	WorkTemperture(75),
	/**
	 * 枚举变量
	 */
	PowerConsume(76),
	/**
	 * 枚举变量
	 */
	Voltage(77),
	/**
	 * 平均I/O响应时间(ms)
	 */
	AverageIOResponseTime(78),
	/**
	 * 枚举变量
	 */
	MaxIOResponseTime(79),
	/**
	 * 枚举变量
	 */
	DirtyPageRatio(80),
	/**
	 * 枚举变量
	 */
	RefreshCacheDivideWriteRequest(81),
	/**
	 * 枚举变量
	 */
	RefreshCacheBandWidth(82),
	/**
	 * 枚举变量
	 */
	RefreshCacheBecauseHightWater(83),
	/**
	 * 枚举变量
	 */
	RefreshCacheBecauseScheduleTimer(84),
	/**
	 * 枚举变量
	 */
	CacheLowWater(85),
	/**
	 * 枚举变量
	 */
	MaxBandWidthWrite(86),
	/**
	 *
	 * 枚举变量
	 */
	FetchFlux(87),
	/**
	 * 枚举变量
	 */
	FetchRatio(88),
	/**
	 * 枚举变量
	 */
	ReadCacheHit(89),
	/**
	 * 枚举变量
	 */
	ReadCacheNotHit(90),
	/**
	 * 枚举变量
	 */
	ReadCacheHitWhenWrite(91),
	/**
	 * 枚举变量
	 */
	ReadCacheHitWhenRead(92),
	/**
	 * 枚举变量
	 */
	ReadCacheRatio(93),
	/**
	 * 枚举变量
	 */
	ReadCacheReHitRatio(94),
	/**
	 * 枚举变量
	 */
	WriteCacheHitRatio(95),
	/**
	 * 枚举变量
	 */
	WriteCacheReHitRatio(96),
	/**
	 * 枚举变量
	 */
	WriteCacheHit(97),
	/**
	 * 枚举变量
	 */
	WriteCacheNotHit(98),
	/**
	 * 枚举变量
	 */
	AsyncTime(99),
	/**
	 * 枚举变量
	 */
	AsyncData(100),
	/**
	 * 枚举变量
	 */
	ReadeRequestCntFromSnapshot(101),
	/**
	 * 枚举变量
	 */
	ReadeRequestCntFromSourceLUN(102),
	/**
	 * 枚举变量
	 */
	WriteRequestCntFromSourceLUN(103),
	/**
	 * 枚举变量
	 */
	ReadRequestCntSnapshotPool(104),
	/**
	 * 枚举变量
	 */
	WriteRequestCntSnapshotPool(105),
	/**
	 * 枚举变量
	 */
	WriteRequestCntMorethanChunk(106),
	/**
	 * 枚举变量
	 */
	ChunkCntUsedBySnapshotCopy(107),
	/**
	 * 枚举变量
	 */
	LocalCacheUsage(108),
	/**
	 * 枚举变量
	 */
	MirrorCacheUsage(109),
	/**
	 * 枚举变量
	 */
	LocalReadCacheRatio(110),
	/**
	 * 枚举变量
	 */
	TimeForHostToSendData(111),
	/**
	 * 枚举变量
	 */
	TimeForSavingWriteData(112),
	/**
	 * 枚举变量
	 */
	TimeForReleasingWriteRequestResources(113),
	/**
	 * 枚举变量
	 */
	TimeForExecutingAWriteRequest(114),
	/**
	 * 枚举变量
	 */
	TimeForReleasingReadRequestResources(115),
	/**
	 * 枚举变量
	 */
	CacheReadIOLatency(116),
	/**
	 * 枚举变量
	 */
	CacheWriteIOLatency(117),
	/**
	 * 枚举变量
	 */
	CacheMirrorIOLatency(118),
	/**
	 * 枚举变量
	 */
	TimeForAllocatingMemoryToAWriteRequest(119),
	/**
	 * 枚举变量
	 */
	CacheWriteUsage(120),
	/**
	 * 枚举变量
	 */
	NumberOfCacheDataFlushIO(121),
	/**
	 * 枚举变量
	 */
	NumberOfCacheDataFlushPages(122),
	/**
	 * 枚举变量
	 */
	ReadBandwidthKB(123),
	/**
	 * 枚举变量
	 */
	WriteBandwidthKB(124),
	/**
	 * 枚举变量
	 */
	AverageReadIOControllerA(125),
	/**
	 * 枚举变量
	 */
	AverageWriteIOControllerA(126),
	/**
	 * 枚举变量
	 */
	MaxIOLatencyControllerA(127),
	/**
	 * 枚举变量
	 */
	AverageIOLatencyControllerA(128),
	/**
	 * 枚举变量
	 */
	UsageRatioControllerA(129),
	/**
	 * 枚举变量
	 */
	QueueLengthControllerA(130),
	/**
	 * 枚举变量
	 */
	ServiceTimeControllerA(131),
	/** 枚举变量 */

	/**
	 * 枚举变量
	 */
	NumberOfCombinedReadIOsPerSecondControllerA(132),
	/** 枚举变量 */

	/**
	 * 枚举变量
	 */
	NumberOfCombinedWriteIOsPerSecondControllerA(133),
	/**
	 * 枚举变量
	 */
	ReadWaitingTimeControllerA(134),
	/**
	 * 枚举变量
	 */
	WriteWaitingTimeControllerA(135),
	/**
	 * 枚举变量
	 */
	AverageIOSizeControllerA(136),
	/**
	 * 枚举变量
	 */
	AverageReadIOControllerB(137),
	/**
	 * 枚举变量
	 */
	AverageWriteIOControllerB(138),
	/**
	 * 枚举变量
	 */
	MaxIOLatencyControllerB(139),
	/**
	 * 枚举变量
	 */
	AverageIOLatencyControllerB(140),
	/**
	 * 枚举变量
	 */
	UsageRatioControllerB(141),
	/**
	 * 枚举变量
	 */
	QueueLengthControllerB(142),
	/**
	 * 枚举变量
	 */
	ServiceTimeControllerB(143),
	/** 枚举变量 */

	/**
	 * 枚举变量
	 */
	NumberOfCombinedReadIOsPerSecondControllerB(144),
	/** 枚举变量 */

	/**
	 * 枚举变量
	 */
	NumberOfCombinedWriteIOsPerSecondControllerB(145),
	/**
	 * 枚举变量
	 */
	ReadWaitingTimeControllerB(146),
	/**
	 * 枚举变量
	 */
	WriteWaitingTimeControllerB(147),
	/**
	 * 枚举变量
	 */
	AverageIOSizeControllerB(148),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution4KB(149),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution8KB(150),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution16KB(151),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution32KB(152),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution64KB(153),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution128KB(154),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution256KB(155),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution512KB(156),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution1MB(157),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistribution2MB(158),
	/**
	 * 枚举变量
	 */
	ReadIOGranularityDistributionMoreThan2MB(159),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution4KB(160),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution8KB(161),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution16KB(162),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution32KB(163),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution64KB(164),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution128KB(165),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution256KB(166),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution512KB(167),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution1MB(168),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistribution2MB(169),
	/**
	 * 枚举变量
	 */
	WriteIOGranularityDistributionMoreThan2MB(170),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution4KB(171),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution8KB(172),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution16KB(173),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution32KB(174),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution64KB(175),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution128KB(176),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution256KB(177),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution512KB(178),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution1MB(179),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistribution2MB(180),
	/**
	 * 枚举变量
	 */
	ReadAndWriteIOGranularityDistributionMoreThan2MB(181),
	/**
	 * 枚举变量
	 */
	OPS(182),
	/**
	 * 枚举变量
	 */
	NumberofOnlineUser(183),
	/**
	 * 枚举变量
	 */
	FileSpaceHardQuota(184),
	/**
	 * 枚举变量
	 */
	UsedCapacity(185),
	/**
	 * 枚举变量
	 */
	TotalCapacity(186),
	/**
	 * 枚举变量
	 */
	DedupeRate(187),
	/**
	 * 枚举变量
	 */
	DiskBusyRatio(192),
	/**
	 * 枚举变量
	 */
	PackagesRate(193),
	/**
	 * 枚举变量
	 */
	RvgBandWidth(194),
	/**
	 * 枚举变量
	 */
	ErrorPackage(190),
	/**
	 * 枚举变量
	 */
	DroppedPackage(189),
	/**
	 * 枚举变量
	 */
	OverrunPackage(191),
	/**
	 * 枚举变量
	 */
	XFerPackage(188),
	/**
	 * 枚举变量
	 */
	MaxReadIOLatency(195),
	/**
	 * 枚举变量
	 */
	MaxWriteIOLatency(196),
	/**
	 * 枚举变量
	 */
	AverageReadIOLatency(197),
	/**
	 * 枚举变量
	 */
	AverageWriteIOLatency(198),
	/**
	 * 枚举变量
	 */
	ReadIOLatencyDistribution10ms(199),
	/**
	 * 枚举变量
	 */
	ReadIOLatencyDistribution20ms(200),
	/**
	 * 枚举变量
	 */
	ReadIOLatencyDistribution50ms(201),
	/**
	 * 枚举变量
	 */
	ReadIOLatencyDistribution100ms(202),
	/**
	 * 枚举变量
	 */
	ReadIOLatencyDistribution200ms(203),
	/**
	 * 枚举变量
	 */
	ReadIOLatencyDistributionMoreThan200ms(204),
	/**
	 * 枚举变量
	 */
	WriteIOLatencyDistribution10ms(205),
	/**
	 * 枚举变量
	 */
	WriteIOLatencyDistribution20ms(206),
	/**
	 * 枚举变量
	 */
	WriteIOLatencyDistribution50ms(207),
	/**
	 * 枚举变量
	 */
	WriteIOLatencyDistribution100ms(208),
	/**
	 * 枚举变量
	 */
	WriteIOLatencyDistribution200ms(209),
	/**
	 * 枚举变量
	 */
	WriteIOLatencyDistributionMoreThan200ms(210),
	/**
	 * 枚举变量
	 */
	ReadandWriteIOLatencyDistribution10ms(211),
	/**
	 * 枚举变量
	 */
	ReadandWriteIOLatencyDistribution20ms(212),
	/**
	 * 枚举变量
	 */
	ReadandWriteIOLatencyDistribution50ms(213),
	/**
	 * 枚举变量
	 */
	ReadandWriteIOLatencyDistribution100ms(214),
	/**
	 * 枚举变量
	 */
	ReadandWriteIOLatencyDistribution200ms(215),
	/**
	 * 枚举变量
	 */
	ReadandWriteIOLatencyDistributionMoreThan200ms(216),
	/**
	 * 枚举变量
	 */
	MaxCPUUsage(217),
	/**
	 * 枚举变量
	 */
	MaxMemoryUsage(218),
	/**
	 * 枚举变量
	 */
	NFSBandwidth(219),
	/**
	 * 枚举变量
	 */
	NFSInBandwidth(220),
	/**
	 * 枚举变量
	 */
	NFSOutBandwidth(221),
	/**
	 * 枚举变量
	 */
	CIFSBandwidth(222),
	/**
	 * 枚举变量
	 */
	CIFSInBandwidth(223),
	/**
	 * 枚举变量
	 */
	CIFSOutBandwidth(224),
	/**
	 * 枚举变量
	 */
	ConnectedClientCount(225),
	/**
	 * 枚举变量
	 */
	ConnectedNFSClientCount(226),
	/**
	 * 枚举变量
	 */
	ConnectedCIFSClientCount(227),
	/**
	 * 枚举变量
	 */
	AverageIOSize(228),
	/**
	 * 枚举变量
	 */
	NetworkPacketRate(229),
	/**
	 * 枚举变量
	 */
	NetworkInboundPacketRate(230),
	/**
	 * 枚举变量
	 */
	NetworkOutboundPacketRate(231),
	/**
	 * 枚举变量
	 */
	ReadOPS(232),
	/**
	 * 枚举变量
	 */
	WriteOPS(233),
	/**
	 * 枚举变量
	 */
	NetTraffic(236),
	/**
	 * 读L2Cache命中次数（次/秒）
	 */
	ReadL2CacheHit(237),
	/**
	 * 读L2Cache未命中次数（次/秒）
	 */
	ReadL2CacheNotHit(238),
	/**
	 * 读L2Cache命中率（%）
	 */
	ReadL2CacheHitRatio(239),
	/**
	 * 平均队列深度
	 */
	AverageQueueSize(240),
	/**
	 * 每秒磁盘完成的总SCSI命令
	 */
	CompleteSCSICommandsPerSecond(241),
	/**
	 * 每秒Verify命令次数
	 */
	VerifyCommandsPerSecond(242),
	/**
	 * 磁盘的总容量（GB）
	 */
	DiskTotalCapacity(243),
	/**
	 * 磁盘的已用容量（GB）
	 */
	DiskAllocedCapacity(244),
	/**
	 * 已用容量百分比
	 */
	DiskAllocedUsage(245),
	/**
	 * 空闲容量百分比
	 */
	DiskFreeUsage(246),
	/**
	 * 脏数据页面数
	 */
	WPCount(247),
	/**
	 * 脏数据页面限制
	 */
	WPLimit(248),
	/**
	 * 脏数据页面占页面的百分比
	 */
	WPUtilization(249),
	/**
	 * 已经使用的cache百分比
	 */
	CacheUsed(250),
	/**
	 * 每秒主机发送的读写IO能够立即在cache中找到的数目
	 */
	HostHit(251),
	/**
	 * 每秒主机发送的读IO能够立即在cache中找到的数目
	 */
	HostReadHit(252),
	/**
	 * 每秒主机发送的写IO能够立即在cache中找到的数目
	 */
	HostWriteHit(253),
	/**
	 * 每秒主机发送的读写IO在cache中不能立即找到的数目
	 */
	HostMiss(254),
	/**
	 * 每秒主机发送的读IO在cache中不能立即找到的数目
	 */
	HostReadMiss(255),
	/**
	 * 每秒主机发送的写IO在cache中不能立即找到的数目
	 */
	HostWriteMiss(256),
	/**
	 * 后端从磁盘到cache完成的读写请求数
	 */
	BEReq(257),
	/**
	 * 后端从磁盘到cache完成的读请求数
	 */
	BEReadReq(258),
	/**
	 * 后端从磁盘到cache完成的写请求数
	 */
	BEWriteReq(259),
	/**
	 * 每秒的后端流量
	 */
	BEMBstransferred(260),
	/**
	 * 每秒的后端读流量
	 */
	BEMBsRead(261),
	/**
	 * 每秒的后端写流量
	 */
	BEMBsWritten(262),
	/**
	 * 读丢失响应时间
	 */
	ReadMissResponseTime(263),
	/**
	 * 写丢失响应时间
	 */
	WriteMissResponseTime(264),
	/**
	 * 读IO占总IO的百分比
	 */
	ReadPercent(265),
	/**
	 * 写IO占总IO的百分比
	 */
	WritePercent(266),
	/**
	 * 读IO占总读IO的百分比
	 */
	ReadHitPercent(267),
	/**
	 * 写IO占总写IO的百分比
	 */
	WriteHitPercent(268),
	/**
	 * 读丢失百分比
	 */
	ReadMissPercent(269),
	/**
	 * 写丢失百分比
	 */
	WriteMissPercent(270),
	/**
	 * 后端读请求时间（从磁盘端口到cache）
	 */
	BEReadRequestTime(271),
	/**
	 * 后端读响应时间（从磁盘端口到cache）
	 */
	BEDiskReadResponseTime(272),
	/**
	 * 后端读请求排队时间
	 */
	BEReadTaskTime(273),
	/**
	 * 后端读响应时间（ms）
	 */
	BEReadResponseTime(274),
	/**
	 * 后端写响应时间（ms）
	 */
	BEWriteResponseTime(275),
	/**
	 * 后端平均响应时间（ms）
	 */
	BEAvgResponseTime(276),
	/**
	 * 后端读请求百分比
	 */
	BEReadsPercent(277),
	/**
	 * 后端写请求百分比
	 */
	BEWritesPercent(278),
	/**
	 * 前端端口到cache之间的数据传输请求数
	 */
	Request(279),
	/**
	 * 前端端口到cache之间的数据传输读请求数
	 */
	ReadRequest(280),
	/**
	 * 前端端口到cache之间的数据传输写请求数
	 */
	WriteRequest(281),
	/**
	 * 每秒种所有前端端口发送的读请求直接命中数
	 */
	ReadHitRequest(282),
	/**
	 * 每秒种所有前端端口发送的写请求直接命中数
	 */
	WriteHitRequest(283),
	/**
	 * 每秒的请求丢失数
	 */
	MissRequest(284),
	/**
	 * 每秒所有的前端端口丢失的总读请求数
	 */
	ReadMissRequest(285),
	/**
	 * 每秒所有的前端端口丢失的总写请求数
	 */
	WriteMissRequest(286),
	/**
	 * 读请求数占总请求数的百分比
	 */
	ReadRequestPercent(287),
	/**
	 * 写请求数占总请求数的百分比
	 */
	WriteRequestPercent(288),
	/**
	 * 读写请求的命中率百分比
	 */
	HitRequestPercent(289),
	/**
	 * 读请求的命中率百分比
	 */
	ReadRequestHitPercent(290),
	/**
	 * 后端到磁盘的每秒IO数
	 */
	IOs(291),
	/**
	 * 后端端口到cache之间的数据传输请求数
	 */
	Requests(292),
	/**
	 * 后端端口到cache之间的数据传输读请求数
	 */
	ReadRequests(293),
	/**
	 * 后端端口到cache之间的数据传输写请求数
	 */
	WriteRequests(294),
	/**
	 * 读写流量
	 */
	Flow(295),
	/**
	 * 端口的读写流量
	 */
	PortFlow(296),
	/**
	 * 端口的读写IOPS
	 */
	PortIOs(297),
	/**
	 * 每秒的读流量
	 */
	ReadFlow(298),
	/**
	 * 每秒的写流量
	 */
	WrittenFlow(299),
	/**
	 * 通过端口的平均IO请求数
	 */
	PortAvgReqSize(300),
	/**
	 * 脏数据页面最大数
	 */
	MaxWPThreshold(301),
	/**
	 * 每秒随机命中数
	 */
	RandomHits(302),
	/**
	 * 命中百分比
	 */
	HitPercent(303),
	/**
	 * 未命中率
	 */
	Missercent(304),
	/**
	 * 随机读百分比
	 */
	RandomReadsPercent(305),
	/**
	 * 随机写百分比
	 */
	RandomWritesPercent(306),
	/**
	 * 最大IOPS(个/秒)
	 */
	MaxIOPSsPerSec(307),
	/**
	 * 执行失败的总IO数（个）
	 */
	FailedIOs(308),
	/**
	 * 每秒的失败IO数（个/秒）
	 */
	FailedIOsPerSec(309),
	/**
	 * 故障的IO率（%）
	 */
	FailedIORatio(310),
	/**
	 * 磁盘吞吐量 311
	 */
	SNAS_DISK_THROUGHPUT(311),
	/**
	 * 磁盘读出数据流量 312
	 */
	SNAS_DISK_READ_DATA_THROUGHPUT(312),
	/**
	 * 磁盘写入数据流量 313
	 */
	SNAS_DISK_WRITE_DATA_THROUGHPUT(313),
	/**
	 * Cache水位
	 */
	Cache_Water(333),
	/**
	 * 读流量
	 */
	Read_MBs(334),
	/**
	 * 写流量
	 */
	Write_MBs(335), t351(351), t352(352), t353(353), t354(354), t355(355), t356(
			/** 写流量 */
			356),t357(357), t358(358), t359(359), t360(360), t361(361), t362(
					/** 写流量 */
					362),t363(363), t364(364), t365(365), t366(366), t367(367), t368(
							/** 写流量 */
							368),

	/**
	 * 平均I/O响应时间(us)
	 */
	AverageIOResponseTimeUs(370),
	/**
	 * 最大I/O响应时间(us)
	 */
	MaxIOResponseTimeUs(371),
	/**
	 * 最大读I/O响应时间(us)
	 */
	MaxReadIOResponseTimeUs(382),
	/**
	 * 最大写I/O响应时间(us)
	 */
	MaxWriteIOResponseTimeUs(383),
	/**
	 * 平均读I/O响应时间(us)
	 */
	AverageReadIOResponseTimeUs(384),
	/**
	 * 平均写I/O响应时间(us)
	 */
	AverageWriteIOResponseTimeUs(385),

	/**
	 * NFS协议操作平均时延(ms)
	 */
	NFS_AVERAGE_LATENCY(410),

	/**
	 * NFS协议操作最大时延(ms)
	 */
	NFS_MAX_LATENCY(411),

	/**
	 * NFS协议写操作平均时延(ms)
	 */
	NFS_WRITE_AVERAGE_LATENCY(412),

	/**
	 * NFS协议写操作最大时延(ms)
	 */
	NFS_WRITE_MAX_LATENCY(413),

	/**
	 * NFS协议读操作平均时延(ms)
	 */
	NFS_READ_AVERAGE_LATENCY(414),

	/**
	 * NFS协议读操作最大时延(ms)
	 */
	NFS_READ_MAX_LATENCY(415),

	/**
	 * CIFS协议操作平均时延(ms)
	 */
	CIFS_AVERAGE_LATENCY(416),

	/**
	 * CIFS协议操作最大时延(ms)
	 */
	CIFS_MAX_LATENCY(417),

	/**
	 * CIFS协议写操作平均时延(ms)
	 */
	CIFS_WRITE_AVERAGE_LATENCY(418),

	/**
	 * CIFS协议写操作最大时延(ms)
	 */
	CIFS_WRITE_MAX_LATENCY(419),

	/**
	 * CIFS协议读操作平均时延(ms)
	 */
	CIFS_READ_AVERAGE_LATENCY(420),

	/**
	 * CIFS协议读操作最大时延(ms)
	 */
	CIFS_READ_MAX_LATENCY(421),

	/**
	 * NFS协议操作平均时延(ms)
	 */
	AVERAGE_LATENCY(428),

	/**
	 * NFS协议操作最大时延(ms)
	 */
	MAX_LATENCY(429),

	/**
	 * NFS协议写操作平均时延(ms)
	 */
	WRITE_AVERAGE_LATENCY(430),

	/**
	 * NFS协议写操作最大时延(ms)
	 */
	WRITE_MAX_LATENCY(431),

	/**
	 * NFS协议读操作平均时延(ms)
	 */
	READ_AVERAGE_LATENCY(432),

	/**
	 * NFS协议读操作最大时延(ms)
	 */
	READ_MAX_LATENCY(433),

	/**
	 * CIFS协议读操作时延连续过大发生次数
	 */
	COUNT_OF_READ_LARGE_LATENCY_CONTINUOUS_OCCURRED_FOR_CIFS(435),

	/**
	 * NFS协议读操作时延连续过大发生次数
	 */
	COUNT_OF_READ_LARGE_LATENCY_CONTINUOUS_OCCURRED_FOR_NFS(436),

	/**
	 * 协议读操作时延连续过大发生次数
	 */
	COUNT_OF_READ_LARGE_LATENCY_CONTINUOUS_OCCURRED(437),

	/**
	 * NFS协议操作最小时延(
	 */
	NFS_MIN_LATENCY(438),

	/**
	 * 吞吐量
	 */
	BPS(439),

	/**
	 * 读IO粒度分布：[0,512B)
	 */
	NFS_SIZE_0K_512B_READ(440),

	/**
	 * 读IO粒度分布：[512B,1K)
	 */
	NFS_SIZE_512B_1K_READ(441),

	/**
	 * 读IO粒度分布：[1K,2K)
	 */
	NFS_SIZE_1K_2K_READ(442),

	/**
	 * 读IO粒度分布：[2K,4K)
	 */
	NFS_SIZE_2K_4K_READ(443),

	/**
	 * 读IO粒度分布：[4K,8K)
	 */
	NFS_SIZE_4K_8K_READ(444),

	/**
	 * 读IO粒度分布：[8K,16K)
	 */
	NFS_SIZE_8K_16K_READ(445),

	/**
	 * 读IO粒度分布：[16K,32K)
	 */
	NFS_SIZE_16K_32K_READ(446),

	/**
	 * 读IO粒度分布：[32K,64K)
	 */
	NFS_SIZE_32K_64K_READ(447),

	/**
	 * 读IO粒度分布：[64K,128K)
	 */
	NFS_SIZE_64K_128K_READ(448),

	/**
	 * 读IO粒度分布：[128K,256K)
	 */
	NFS_SIZE_128K_256K_READ(449),

	/**
	 * 读IO粒度分布：[256K,+∞)
	 */
	NFS_SIZE_OVER_256K_READ(450),

	/**
	 * 读IO粒度分布：[0,512B)
	 */
	NFS_SIZE_0K_512B_WRITE(451),

	/**
	 * 读IO粒度分布：[512B,1K)
	 */
	NFS_SIZE_512B_1K_WRITE(452),

	/**
	 * 读IO粒度分布：[1K,2K)
	 */
	NFS_SIZE_1K_2K_WRITE(453),

	/**
	 * 读IO粒度分布：[2K,4K)
	 */
	NFS_SIZE_2K_4K_WRITE(454),

	/**
	 * 读IO粒度分布：[4K,8K)
	 */
	NFS_SIZE_4K_8K_WRITE(455),

	/**
	 * 读IO粒度分布：[8K,16K)
	 */
	NFS_SIZE_8K_16K_WRITE(456),

	/**
	 * 读IO粒度分布：[16K,32K)
	 */
	NFS_SIZE_16K_32K_WRITE(457),

	/**
	 * 读IO粒度分布：[32K,64K)
	 */
	NFS_SIZE_32K_64K_WRITE(458),

	/**
	 * 读IO粒度分布：[64K,128K)
	 */
	NFS_SIZE_64K_128K_WRITE(459),

	/**
	 * 读IO粒度分布：[128K,256K)
	 */
	NFS_SIZE_128K_256K_WRITE(460),

	/**
	 * 读IO粒度分布：[256K,+∞)
	 */
	NFS_SIZE_OVER_256K_WRITE(461),

	/**
	 * 空间利用率（%）
	 */
	SPACE_USAGE(462),

	/**
	 * 空间大小 (MB)
	 */
	SPACE_SIZE(463),

	/**
	 * 平均读OPS响应时间 (ms)
	 */
	AVERAGE_READ_OPS_RESPONSE_TIME(464),

	/**
	 * 平均写OPS响应时间 (ms)
	 */
	AVERAGE_WRITE_OPS_RESPONSE_TIME(465),

	/**
	 * ReadIOStatistics0BTo512B
	 */
	ReadIOStatistics0BTo512B(466),

	/**
	 * ReadIOStatistics512BTo1KB
	 */
	ReadIOStatistics512BTo1KB(467),

	/**
	 * ReadIOStatisticsMoreThan256KB
	 */
	ReadIOStatisticsMoreThan256KB(468),

	/**
	 * WriteIOStatistics0BTo512B
	 */
	WriteIOStatistics0BTo512B(469),

	/**
	 * WriteIOStatistics512BTo1KB
	 */
	WriteIOStatistics512BTo1KB(470),

	/**
	 * WriteIOStatisticsMoreThan256KB
	 */
	WriteIOStatisticsMoreThan256KB(471),

	/**
	 * 最小操作时延
	 */
	MIN_LATENCY(472),
	/**
	 * 文件带宽
	 */
	FILE_BANDWIDTH(475),
	/**
	 * 文件OPS
	 */
	FILE_OPS(476),
	/**
	 * 平均操作时延微秒
	 */
	AVG_LATENCY_OPS_US(508),
	/**
	 * 最大操作时延微秒
	 */
	MAX_LATENCY_OPS_US(509),
	/**
	 * 最小操作时延微秒
	 */
	MIN_LATENCY_OPS_US(510),
	/**
	 * 逻辑带宽
	 */
	LOGICAL_BANDWITH(473),
	/** Smart Tier 指标 */
	/**
	 * 总的已迁移数据量
	 */
	TOTAL_MOVED_DATA_SIZE(513),
	/**
	 * 已迁往SSD的数据量
	 */
	MOVED_TO_SSD_DATA_SIZE(514),
	/**
	 * 已迁往SAS的数据量
	 */
	MOVED_TO_SAS_DATA_SIZE(515),
	/**
	 * 已迁往NL-SAS的数据量
	 */
	MOVED_TO_NLSAS_DATA_SIZE(516),
	/**
	 * SAS已迁往SSD的数据量
	 */
	SAS_MOVED_TO_SSD_DATA_SIZE(517),
	/**
	 * NL-SAS已迁往SSD的数据量
	 */
	NLSAS_MOVED_TO_SSD_DATA_SIZE(518),
	/**
	 * SSD已迁往SAS的数据量
	 */
	SSD_MOVED_TO_SAS_DATA_SIZE(519),
	/**
	 * NL-SAS已迁往SAS的数据量
	 */
	NLSAS_MOVED_TO_SAS_DATA_SIZE(520),
	/**
	 * SSD已迁往NL-SAS的数据量
	 */
	SSD_MOVED_TO_NLSAS_DATA_SIZE(521),
	/**
	 * SAS已迁往NL-SAS的数据量
	 */
	SAS_MOVED_TO_NLSAS_DATA_SIZE(522),
	// 2015 - 01 -08 新增
	/**
	 * 文件带宽KB/s
	 */
	FILE_BANDWIDTH_KBs(511),
	/**
	 * 吞吐量KB/s 512
	 */
	THROUGHPUT_KBs(512),
	/**
	 * 平均服务时间 微秒 523
	 */
	SERVICE_TIME_US(523),
	/**
	 * 平均读OPS响应时间 微秒 524
	 */
	AVG_READ_OPS_RESPONSE_US(524),
	/**
	 * 平均写OPS响应时间 微秒 525
	 */
	AVG_WRITE_OPS_RESPONSE_US(525),
	//
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_0MS_TO_5MS(530),
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_5MS_TO_10MS(396),
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_15MS_TO_20MS(200),
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_20MS_TO_50MS(201),
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_50MS_TO_100MS(202),
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_100MS_TO_200MS(203),
	/**
	 * 读I/O响应时间分布：
	 */
	READ_IO_LATENCY_DISTRIBUTION_200MSMORE(204),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_0MS_TO_5MS(531),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_5MS_TO_10MS(402),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_15MS_TO_20MS(206),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_20MS_TO_50MS(207),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_50MS_TO_100MS(208),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_100MS_TO_200MS(209),
	/**
	 * 写I/O响应时间分布：
	 */
	WRITE_IO_LATENCY_DISTRIBUTION_200MSMORE(210),
	/**
	 * 读I/O失败数
	 */
	READ_IO_FAILED(532),
	/**
	 * 写I/O失败数
	 */
	WRITE_IO_FAILED(533),
	/**
	 * 容量利用率
	 */
	CAPCITY_USEAGE(90004),
	/**
	 * 文件系统带宽 （KB/s）
	 */
	FILE_SYSTEM_BANDWIDTH(534),
	/**
	 * 文件系统读带宽 （KB/s）
	 */
	FILE_SYSTEM_READ_BANDWIDTH(535),
	/**
	 * 文件系统写带宽（KB/s）
	 */
	FILE_SYSTEM_WRITE_BANDWIDTH(536),
	/**
	 * S3存储系统带宽 （KB/s）
	 */
	S3_BANDWIDTH(537),
	/**
	 * S3存储系统读带宽 （KB/s）
	 */
	S3_READ_BANDWIDTH(538),
	/**
	 * S3存储系统写带宽（KB/s）
	 */
	S3_WRITE_BANDWIDTH(539),
	/**
	 * S3存储业务Delete次数
	 */
	S3_DELETE_NUMBER(540),
	/**
	 * S3存储业务Delete失败次数
	 */
	S3_DELETE_FAILED_NUMBER(541),
	/**
	 * S3存储业务因客户端原因Delete失败次数
	 */
	S3_DELETE_FAILED_NUMBER_CLIENTS_CAUSE(542),
	/**
	 * S3存储业务Get请求次数
	 */
	S3_GET_NUMBER(543),
	/**
	 * S3存储业务Get请求失败次数
	 */
	S3_GET_FAILED_NUMBER(544),
	/**
	 * S3存储业务因客户端原因Get请求失败次数
	 */
	S3_GET_FAILED_NUMBER_CLIENTS_CAUSE(545),
	/**
	 * S3存储业务Put请求次数
	 */
	S3_PUT_NUMBER(546),
	/**
	 * S3存储业务Put请求失败次数
	 */
	S3_PUT_FAILED_NUMBER(547),
	/**
	 * S3存储业务因客户端原因Put请求失败次数
	 */
	S3_PUT_FAILED_NUMBER_CLIENTS_CAUSE(548),
	/**
	 * S3存储业务业务接入失败率
	 */
	S3_CONNECT_FAILED_RATE(549),
	/**
	 * S3存储业务Head次数
	 */
	S3_HEAD_NUMBER(1061),
	/**
	 * S3存储业务Head失败次数
	 */
	S3_HEAD_FAILED_NUMBER(1062),
	/**
	 * S3存储业务因客户端原因Head失败次数
	 */
	S3_HEAD_FAILED_NUMBER_CLIENTS_CAUSE(1063),
	/**
	 * S3存储业务Post次数
	 */
	S3_POST_NUMBER(1064),
	/**
	 * S3存储业务Post失败次数
	 */
	S3_POST_FAILED_NUMBER(1065),
	/**
	 * S3存储业务因客户端原因Post失败次数
	 */
	S3_POST_FAILED_NUMBER_CLIENTS_CAUSE(1066),
	/**
	 * Swift存储系统带宽 （KB/s）
	 */
	SWIFT_BANDWIDTH(550),
	/**
	 * Swift存储系统读带宽 （KB/s）
	 */
	SWIFT_READ_BANDWIDTH(551),
	/**
	 * Swift存储系统写带宽（KB/s）
	 */
	SWIFT_WRITE_BANDWIDTH(552),
	/**
	 * Swift存储业务Delete次数
	 */
	SWIFT_DELETE_NUMBER(553),
	/**
	 * Swift存储业务Delete失败次数
	 */
	SWIFT_DELETE_FAILED_NUMBER(554),
	/**
	 * Swift存储业务因客户端原因Delete失败次数
	 */
	SWIFT_DELETE_FAILED_NUMBER_CLIENTS_CAUSE(555),
	/**
	 * Swift存储业务Get请求次数
	 */
	SWIFT_GET_NUMBER(556),
	/**
	 * Swift存储业务Get请求失败次数
	 */
	SWIFT_GET_FAILED_NUMBER(557),
	/**
	 * Swift存储业务因客户端原因Get请求失败次数
	 */
	SWIFT_GET_FAILED_NUMBER_CLIENTS_CAUSE(558),
	/**
	 * Swift存储业务Put请求次数
	 */
	SWIFT_PUT_NUMBER(559),
	/**
	 * Swift存储业务Put请求失败次数
	 */
	SWIFT_PUT_FAILED_NUMBER(560),
	/**
	 * Swift存储业务因客户端原因Put请求失败次数
	 */
	SWIFT_PUT_FAILED_NUMBER_CLIENTS_CAUSE(561),
	/**
	 * Swift存储业务接入失败率
	 */
	SWIFT_CONNECT_FAILED_RATE(562),
	/**
	 * Swift存储业务Head次数
	 */
	SWIFT_HEAD_NUMBER(1067),
	/**
	 * Swift存储业务Head失败次数
	 */
	SWIFT_HEAD_FAILED_NUMBER(1068),
	/**
	 * Swift存储业务因客户端原因Head失败次数
	 */
	SWIFT_HEAD_FAILED_NUMBER_CLIENTS_CAUSE(1069),
	/**
	 * Swift存储业务Post次数
	 */
	SWIFT_POST_NUMBER(1070),
	/**
	 * Swift存储业务Post失败次数
	 */
	SWIFT_POST_FAILED_NUMBER(1071),
	/**
	 * Swift存储业务因客户端原因Post失败次数
	 */
	SWIFT_POST_FAILED_NUMBER_CLIENTS_CAUSE(1072),

	/**
	 * 同步持续时间
	 */
	SYNCHRONIZATION_DURATION(1054),
	/**
	 * Cache的Page水位
	 */
	CACHE_PAGE_UTILIZATION(1055),
	/**
	 * Cache的Chunk水位
	 */
	CACHE_CHUNK_UTILIZATION(1056),
	/**
	 * Cache的PageUnit水位
	 */
	CACHE_PAGEUNIT_UTILIZATION(1057),
	/**
	 * Cache Page保有量
	 */
	THE_CACHE_PAGE_PRESERVATION(1059),
	/**
	 * Cache Chunk保有量
	 */
	THE_CACHE_CHUNK_PRESERVATION(1060),

	/**
	 * SCSI IOPS(IO/s)
	 */
	SCSI_IOPS(1079),
	/**
	 * ISCSI IOPS (IO/s)
	 */
	ISCSI_IOPS(1073),
	/**
	 * NFS每秒operation次数
	 */
	NFS_OPERSTION_COUNT_PER_SECOND(627),
	/**
	 * CIFS每秒operation次数
	 */
	CIFS_OPERSTION_COUNT_PER_SECOND(1074),
	/**
	 * 磁盘总IOPS(IO/s)
	 */
	TOTAL_DISK_IOPS(1076),
	/**
	 * 磁盘读IOPS(IO/s)
	 */
	READ_DISK_IOPS(1077),
	/**
	 * 磁盘读IOPS(IO/s)
	 */
	WRITE_DISK_IOPS(1078),
	/**
	 * 磁盘最大利用率(%)
	 */
	DISK_MAX_USAGE(1075),

	// 总容量
	Total_Capacity(10000),
	// 已用容量
	Used_Capacity(10001),
	// 空闲容量
	Free_Capacity(10002),
	// 容量利用率
	Capacity_Usage_Rate(10003),
	// 空闲容量百分比
	Capacity_Free_Rate(10004),

	//文件IO操作总数(高32位) SMIS - FS
	TOTAL_FS_IO_COUNT_HIGH(1024),

	//文件IO操作总数(低32位) SMIS - FS
	TOTAL_FS_IO_COUNT_LOW(1025),

	//文件传输字节总数（Kbytes）(高32位) SMIS - FS
	TOTAL_FS_KBYTES_TRANSFERRED_HIGH(1026),
	//传输字节总数（Kbytes）(低32位) SMIS - FS
	TOTAL_FS_KBYTES_TRANSFERRED_LOW(1027),

	//读IO总数(高32位) SMIS - FS
	TOTAL_FS_READ_IO_HIGH(1028),
	//读IO总数(低32位) SMIS - FS
	TOTAL_FS_READ_IO_LOW(1029),

	//写IO总数(高32位) SMIS - FS
	TOTAL_FS_WRITE_IO_HIGH(1030),
	//写IO总数(低32位) SMIS - FS
	TOTAL_FS_WRITE_IO_LOW(1031),

	//操作元数据（创建文件，目录等）累积计数(高32位) SMIS - FS
	TOTAL_FS_OTHER_IO_HIGH(1032),
	//操作元数据（创建文件，目录等）累积计数(低32位) SMIS - FS
	TOTAL_FS_OTHER_IO_LOW(1033),

	//SAN IO操作总数(高32位) SMIS - SAN
	TOTAL_SAN_IO_COUNT_HIGH(1000),

	//SAN IO操作总数(低32位) SMIS - SAN
	TOTAL_SAN_IO_COUNT_LOW(1001),

	//累加传输字节总数（Kbytes）(高32位)	SMIS - SAN
	TOTAL_SAN_KBYTES_TRANSFERRED_HIGH(1002),
	//累加传输字节总数（Kbytes）(低32位)	SMIS - SAN
	TOTAL_SAN_KBYTES_TRANSFERRED_LOW(1003),

	//TotalIOs累积运行时间（ms）	SMIS - SAN
	TOTAL_SAN_IO_TIME_COUNT_HIGH(1004),
	//TotalIOs累积运行时间（ms）(低32位)	SMIS - SAN
	TOTAL_SAN_IO_TIME_COUNT_LOW(1005),


	//读IO总数(高32位)	SMIS - SAN
	TOTAL_SAN_READ_IO_HIGH(1006),
	//读IO总数(低32位)	SMIS - SAN
	TOTAL_SAN_READ_IO_LOW(1007),


    //读取缓存命中的累积计数(高32位)	SMIS - SAN
    TOTAL_SAN_READ_IO_HIT_CACHE_HIGH(1008),

    //读取缓存命中的累积计数(低32位)	SMIS - SAN
    TOTAL_SAN_READ_IO_HIT_CACHE_LOW(1009),

    //ReadIOs累积运行时间(ms)(高32位)	SMIS - SAN
    TOTAL_SAN_READ_IO_TIME_COUNT_HIGH(1010),
    //ReadIOs累积运行时间(ms)(低32位)	SMIS - SAN
    TOTAL_SAN_READ_IO_TIME_COUNT_LOW(1011),

    //读累积Kbytes(高32位)	SMIS - SAN
    TOTAL_SAN_KBYTES_READ_HIGH(1012),
    //读累积Kbytes(低32位)	SMIS - SAN
    TOTAL_SAN_KBYTES_READ_LOW(1013),

	//写IO总数(高32位)	SMIS - SAN
	TOTAL_SAN_WRITE_IO_HIGH(1014),
	//写IO总数(低32位)	SMIS - SAN
	TOTAL_SAN_WRITE_IO_LOW(1015),

    //写缓存的IO总数(高32位)	SMIS - SAN
    TOTAL_SAN_WRITE_IOS_HIT_CACHE_HIGH(1016),
    //写缓存的IO总数(低32位)	SMIS - SAN
    TOTAL_SAN_WRITE_IOS_HIT_CACHE_LOW(1017),

    //WriteIOs累积运行时间(高32位)	SMIS - SAN
    TOTAL_SAN_WRITE_IO_TIME_COUNTER_HIGH(1018),
    //WriteIOs累积运行时间(低32位)	SMIS - SAN
    TOTAL_SAN_WRITE_IO_TIME_COUNTER_LOW(1019),
    //写累积Kbytes(高32位)	SMIS - SAN
    TOTAL_KBYTES_WRITTEN_HIGH(1020),
    //写累积Kbytes(低32位)	SMIS - SAN
    TOTAL_KBYTES_WRITTEN_LOW(1021),
    //在阵列中的所有空闲时间的单位时间的累积数(ms)(高32位)	SMIS - SAN
    TOTAL_SAN_IDLE_TIME_COUNT_HIGH(1022),
    //在阵列中的所有空闲时间的单位时间的累积数(ms)(低32位)	SMIS - SAN
    TOTAL_SAN_IDLE_TIME_COUNT_LOW(1023);




	// 2015 - 01 -08 新增 结束
	private int value;

	/**
	 * 构造函数
	 */
	DataType(int value) {
		this.value = value;
	}

	/**
	 * 获取统计数据类型整型值
	 *
	 * @return int 统计数据类型整型值
	 */
	public int getValue() {
		return this.value;
	}

	private static Map<Integer, DataType> c = new HashMap<Integer, DataType>(DataType.values().length);

	static {

		for (DataType value : DataType.values()) {
			c.put(value.getValue(), value);
		}

	}

	/**
	 * 根据数字常量查询枚举值
	 *
	 * @param intValue
	 *            方法参数：intValue
	 * @return DataType 返回结果
	 * @author y90003176
	 */
	public static DataType valueOf(int intValue) {
		return c.get(intValue);
	}



}
