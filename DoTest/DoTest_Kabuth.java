public class DoTest_Kabuth {
	// Why test?
	//
	// - Because you never know what a program does until you run it,
	//   even if you wrote it yourself.
	//
	// Testing sounds harder than it actually is. You don't have to
	// use JUnit or any other fancy frameworks. To test a feature,
	// run it on input X, and check its output against what you expect.
	// It's that simple.
	//
	// Let's put tests in the main method as a start.

	public static void main(String[] args) {
		// to be filled with tests

		sumTest(); // to be uncommented in exercise 1 step 4.
		sortTest();
		//println test sort algorithm
		int[] x =bubblesort(new int[]{8,14,12,3,1});
		   for(int a=0;a<5;a++){
		   System.out.println(x[a]);
		   }
	}

	// Exercise 1: Is this your first test?
	// ====================================

	// Implement and test a function computing the sum of an array
	// of integers.

	// Step 1 [done]: Think about what we want to implement, and come up
	// with an interface. We want to sum an array of integers.
	// The interface should be a function from int[] to int.

	static int sum(int[] array) {
		int x = 0;
		for(int a:array){
			x+=a;
		}
		// Step 2 [done]: Write minimum code to make it compile.
		return x;
	}

	// Step 3 [done]: Write the test.

	static void sumTest() {
		// Tests are simply checks of argument-result pairs.
		assert sum(new int[]{}) == 0;
		System.out.println("ok: sum {} == 0");

		// Step 4: Call sumTest() in main method and run it.
		//
		// > javac DoTest.java
		// > java -ea DoTest
		//
		// The -ea flag enables assertions.
		// It should execute without incident.

		// Step 5: Write another test, such as:
		//
		assert sum(new int[]{1}) == 1;
		System.out.println("ok: sum {1} == 1");
		//
		// If you run it with
		//
		// > java -ea DoTest
		//
		// then you should get the following error message:
		// 
		//   Exception in thread "main" java.lang.AssertionError
		//   at DoTest.sumTest(DoTest.java:53)
		//   at DoTest.main(DoTest.java:17)
		//
		// Going back to line 53, we see that the sum of {1}
		// is not 1.

		// Step 6: Go back to the definition of sum.
		// Write enough code to make the test pass.
		//
		// ...

		// Step 7: We know sum({}) == 0 and sum({1}) == 1.
		// What about other inputs? Write more tests to
		// be more confident that sum is working correctly.

		assert sum(new int[]{1, 2, 3, 4}) == 10;
		assert sum(new int[]{3, 4, 5, 2}) == 14;
		assert sum(new int[]{1, 1, 1, 1}) == 4;
		assert sum(new int[]{9, 1, 2, 8}) == 20;
		assert sum(new int[]{11, 14, 5, 2}) == 32;
		assert sum(new int[]{3, 4, 3, 5}) == 15;
		//
		// assert ... (write some of your own)

		// Step 8: What should happen in boundary cases?
		//
		assert sum(null) == 0;

		System.out.println("sumTest passed.");
	}


	// Exercise 2: Practice test-driven development some more
	// ======================================================
	//
	// Please test a sorting algorithm of your choice.
	// Do go through all the steps we just learnt.

	// Step 1: Come up with an interface for the feature.
	//
	static int[] bubblesort(int[] zusortieren) {
		int temp;
		for(int i=1; i<zusortieren.length; i++) {
			for(int j=0; j<zusortieren.length-i; j++) {
				if(zusortieren[j]>zusortieren[j+1]) {
					temp=zusortieren[j];
					zusortieren[j]=zusortieren[j+1];
					zusortieren[j+1]=temp;
				}	
			}
		}
		return zusortieren;
	}

	// Step 2: Write minimum code to make it compile.

	// Step 3: Write some really obvious tests like
	//
	static void sortTest() {
		assert bubblesort(new int[]{1,3,2,5,4}) == (new int[]{1,2,3,4,5}) ;
		assert bubblesort(new int[]{13,4,5,7}) == (new int[]{4,5,7,13}) ;
		assert bubblesort(new int[]{8,14,12,3,1}) == (new int[]{1,3,8,12,14}) ;
		assert bubblesort(new int[]{}) == (new int[]{}) ;
	}

	// Step 4: Call sortTest in main and run it.
	// Go ahead, do it.
	// Don't forget to run with the -ea flag: `java -ea DoTest`

	// Step 5--6: If the really obvious test succeeds, it's time
	// to write more reasonable tests. If the obvious test fails,
	// you can go to step 7 directly, though more tests won't
	// hurt.

	// Step 7: Go back to the definition of `sort` and write
	// enough code to make tests pass.

	// Step 8: If you can think of some boundary conditions,
	// write some tests for those as well. If they fail, then
	// change the implementation of `sort` until they pass.
	
	// Exercise 3: The real deal
	// =========================
	//
	// Apply the technique here to test one small functionality
	// of your group's implementation of "Rome and Carthage".
	// Every person must do this on their own. Coordinate with
	// your group mates so that in doing this exercise, you get
	// as many features tested as possible. You may use testing
	// frameworks like JUnit to reduce the amount of boilerplate
	// code one has to write.
	//
	// Deadline: Thursday, 22.05.2014
	//
	// Action item: Each person should take a screenshot showing
	// 1. the test code, and
	// 2. the output of a console after a successful test run.


	// Exercise 4: Homework submission by git
	// ======================================
	//
	// Push this worksheet and your screenshot of Exercise 3
	// to your group's repository.  Here's a guide about
	// accessing the repository.
	//
	// First, install the version control tool `git`. It's
	// ubiquitous on Linux distributions and comes with XCode on
	// Mac. On Windows, one can install `msysgit`.
	//
	//     http://msysgit.github.io/
	//
	// All group repositories are hosted on the machine
	// `plse.mathematik.uni-marburg.de`. If you are in group 42,
	// run the following command to download a copy of the repo.
	//
	//     git clone https://plse.mathematik.uni-marburg.de:7179/42.git
	//
	// Change /42.git in the URL to /n.git if you are in group n.
	// The console should ask you for username and password.
	// We will email you those before Monday, 19.05.2014.
	// If you get "fatal: Authentication failed" without
	// entering the password, then try
	//
	//     git credential-cache exit
	//
	// before cloning /42.git again.
	//
	// (If you get an SSL error, then you should install the root
	// certificate of the University of Marburg. If it's not
	// possible to install the certificate, then you may disable
	// SSL verification for homework submission by typing
	// `-c http.sslVerify=false` before every git operation.
	// For example, the clone command becomes
	// `git -c http.sslVerify=false clone ...`. However, this is
	// VERY DANGEROUS. Do not ever do it in a company.)
	//
	// Those commands will tell `git` to ignore the fact that
	// the SSL certificate on `plse` is not signed by any
	// authority. This way we get to enjoy encrypted communication
	// without paying money.
	//
	// In the folder `42` (or rather, your group number), create
	// a new folder `screenshots` if it weren't already there.
	// put your screenshots of testing code into the folder.
	// Run in console:
	//
	//     git status
	//
	// You should see some lines like:
	//
	// >   Untracked files:
	// >     (use "git add <file>..." to include in what will be committed)
	// >
	// >           screenshots/
	// >
	// >   nothing added to commit but untracked files present (use "git add" to track)
	//
	// Then run the command
	//
	//     git add screenshots/Mustermann.png  # or rather, path to your own screenshot
	//
	// Typing `git status` again should show:
	//
	// >   Changes to be committed:
	// >     (use "git rm --cached <file>..." to unstage)
	// >
	// >           new file:    screenshots/Mustermann.png
	//
	// Save the change on your local machine by typing
	//
	//     git commit -m "add Mustermann's screenshot"
	//
	// (Adapt Mustermann's message to something sensible in
	// your own case.) The console should display something
	// similar to:
	//
	// >   [master 3761db4] add Mustermann's screenshot
	// >    1 file changed, 0 insertions(+), 0 deletions(-)
	// >    create mode 100644 screenshots/Mustermann.png
	//
	// Now upload your screenshot to `plse` by:
	//
	//     git push
	//
	// If the console displays
	//
	// >   Counting objects: 4, done.
	// >   Delta compression using up to 4 threads.
	// >   Compressing objects: 100% (2/2), done.
	// >   Writing objects: 100% (4/4), 383.24 KiB | 0 bytes/s, done.
	// >   Total 4 (delta 0), reused 0 (delta 0)
	// >   To https://plse.mathematik.uni-marburg.de:7179/42.git
	//
	// then the teaching team will be able to view your
	// screenshot submission. If there's an error, google
	// the error message for help.


	// Exercise 5: Use the group repository
	// ====================================
	//
	// After everybody in the group managed exercise 4 successfully,
	// the boss of milestone 0.3 should coordinate put the source
	// code of the course project to the group repository before
	// the deadline of milestone 0.3 on 29.05.14.
	//
	// The repository will make collaboration much easer.
	// People can share their code with `git push`, and get
	// code written by others with `git pull`.
}
