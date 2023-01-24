#include <efsw/FileSystem.hpp>
#include <efsw/System.hpp>
#include <efsw/efsw.hpp>
#include <iostream>
#include <atomic>
#include <fstream>
#include <signal.h>

bool STOP = false;

void sigend( int ) {
	std::cout << std::endl << "Bye bye" << std::endl;
	STOP = true;
}

std::atomic_int modified(0);

// Processes a file action
class UpdateListener : public efsw::FileWatchListener {
  public:
	UpdateListener() {}

	std::string getActionName( efsw::Action action ) {
		switch ( action ) {
			case efsw::Actions::Add:
				return "Add";
			case efsw::Actions::Modified:
				return "Modified";
			case efsw::Actions::Delete:
				return "Delete";
			case efsw::Actions::Moved:
				return "Moved";
			default:
				return "Bad Action";
		}
	}

	void handleFileAction( efsw::WatchID watchid, const std::string& dir,
						   const std::string& filename, efsw::Action action,
						   std::string oldFilename = "" ) {
		/*std::cout << "Watch ID " << watchid << " DIR ("
				  << dir + ") FILE (" +
						 ( oldFilename.empty() ? "" : "from file " + oldFilename + " to " ) +
						 filename + ") has event "
				  << getActionName( action ) << std::endl;*/
		modified.exchange(1);
	}
};

efsw::WatchID handleWatchID( efsw::WatchID watchid ) {
	switch ( watchid ) {
		case efsw::Errors::FileNotFound:
		case efsw::Errors::FileRepeated:
		case efsw::Errors::FileOutOfScope:
		case efsw::Errors::FileRemote:
		case efsw::Errors::Unspecified: {
			std::cout << efsw::Errors::Log::getLastErrorLog().c_str() << std::endl;
			break;
		}
		default: {
			std::cout << "Added WatchID: " << watchid << std::endl;
		}
	}

	return watchid;
}

std::string path;

std::string read() {
	modified.exchange(0);
	while (modified.load() == 0) {
		efsw::System::sleep( 100 );
	}
	std::ifstream file(path + "test.txt");
	std::string line;
    std::getline(file, line);
	return line;
	// file is automatically closed
}

void write(std::string contents) {
	std::ofstream file(path + "test.txt");
	file << contents;
	// file is automatically closed
}

int main( int argc, char** argv ) {
	signal( SIGABRT, sigend );
	signal( SIGINT, sigend );
	signal( SIGTERM, sigend );

	std::cout << "Press ^C to exit" << std::endl;

	bool useGeneric = false;

	UpdateListener* ul = new UpdateListener();

	efsw::FileWatcher fileWatcher( useGeneric );

	fileWatcher.followSymlinks( false );
	fileWatcher.allowOutOfScopeLinks( false );

    // Replace with your own Debug folder path
    // Under Visual Studio Code, build the CMake project then right-click your build/Debug folder, select 'Reveal in File Explorer'
    // Make sure the path to the test.txt file is correct!
	path = "C:\\Users\\fred\\Documents\\WSVSCode\\build\\Debug\\test\\";
	std::string CurPath( path );

	std::cout << "Folder path that will be watched: " << CurPath.c_str() << std::endl;

	fileWatcher.watch();

	efsw::WatchID watchID = handleWatchID( fileWatcher.addWatch( CurPath, ul, true ) );

	while ( !STOP ) {
		// TODO:
		// Use read and write, successively, as in the Java client program
		efsw::System::sleep( 100 );
	}

	// Delete the watch
	if ( watchID > 0 ) {
		efsw::System::sleep( 1000 );
		fileWatcher.removeWatch( watchID );
	}

	return 0;
}
