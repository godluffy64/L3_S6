cmake_minimum_required(VERSION 3.8)

project(efsw)

find_package(Threads REQUIRED)

add_executable(efsw
	src/efsw/main.cpp
	src/efsw/Debug.cpp
	src/efsw/DirectorySnapshot.cpp
	src/efsw/DirectorySnapshotDiff.cpp
	src/efsw/DirWatcherGeneric.cpp
	src/efsw/FileInfo.cpp
	src/efsw/FileSystem.cpp
	src/efsw/FileWatcher.cpp
	src/efsw/FileWatcherCWrapper.cpp
	src/efsw/FileWatcherGeneric.cpp
	src/efsw/FileWatcherImpl.cpp
	src/efsw/Log.cpp
	src/efsw/Mutex.cpp
	src/efsw/String.cpp
	src/efsw/System.cpp
	src/efsw/Thread.cpp
	src/efsw/Watcher.cpp
	src/efsw/WatcherGeneric.cpp
)

target_include_directories(efsw
	PRIVATE src/
	PUBLIC
		$<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
		$<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/src>
		$<INSTALL_INTERFACE:${CMAKE_INSTALL_INCLUDEDIR}>
)
target_compile_features(efsw PUBLIC cxx_std_11)

target_link_libraries(efsw PRIVATE Threads::Threads)

# platforms
if (WIN32)
	target_sources(efsw PRIVATE
		src/efsw/platform/win/FileSystemImpl.cpp
		src/efsw/platform/win/MutexImpl.cpp
		src/efsw/platform/win/SystemImpl.cpp
		src/efsw/platform/win/ThreadImpl.cpp
	)
else ()
	target_sources(efsw PRIVATE
		src/efsw/platform/posix/FileSystemImpl.cpp
		src/efsw/platform/posix/MutexImpl.cpp
		src/efsw/platform/posix/SystemImpl.cpp
		src/efsw/platform/posix/ThreadImpl.cpp
	)
endif()

# watcher implementations

if (APPLE)
	target_sources(efsw PRIVATE
		src/efsw/FileWatcherFSEvents.cpp
		src/efsw/FileWatcherKqueue.cpp
		src/efsw/WatcherFSEvents.cpp
		src/efsw/WatcherKqueue.cpp
	)

	if (NOT CMAKE_SYSTEM_VERSION GREATER 9)
		target_compile_definitions(efsw PRIVATE EFSW_FSEVENTS_NOT_SUPPORTED)
	endif()
elseif (WIN32)
	target_sources(efsw PRIVATE
		src/efsw/FileWatcherWin32.cpp
		src/efsw/WatcherWin32.cpp
	)
elseif (${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	target_sources(efsw PRIVATE
		src/efsw/FileWatcherInotify.cpp
		src/efsw/WatcherInotify.cpp
	)

	if (NOT EXISTS "/usr/include/sys/inotify.h" AND NOT EXISTS "/usr/local/include/sys/inotify.h")
		target_compile_definitions(efsw PRIVATE EFSW_INOTIFY_NOSYS)
	endif()
elseif (${CMAKE_SYSTEM_NAME} MATCHES "FreeBSD")
	target_sources(efsw PRIVATE
		src/efsw/FileWatcherKqueue.cpp
		src/efsw/WatcherKqueue.cpp
	)
endif()

if (MSVC)
	target_compile_definitions(efsw PRIVATE _SCL_SECURE_NO_WARNINGS)
else ()
	target_compile_options(efsw PRIVATE -Wall -Wno-long-long -fPIC)
endif()

target_compile_definitions(efsw PRIVATE $<IF:$<CONFIG:Debug>,DEBUG,NDEBUG>)

if (APPLE)
	set(MAC_LIBS "-framework CoreFoundation" "-framework CoreServices")
	target_link_libraries(efsw PRIVATE ${MAC_LIBS})
elseif (NOT (${CMAKE_SYSTEM_NAME} MATCHES "Haiku") AND NOT WIN32)
	target_link_libraries(efsw PRIVATE Threads::Threads)
endif()

