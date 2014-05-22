import java.util.Arrays;
public class DoTest {

    public static void main(String[] args) {

        sumTest(); // to be uncommented in exercise 1 step 4.
    	bubblesortTest();
    }

    static int sum(int[] array) {
    	int k = 0;
    	for (int i : array) {
    		k = k + i;
    	}
        return k;
    }


    static void sumTest() {

        assert sum(new int[]{}) == 0;
        System.out.println("ok: sum {} == 0");

        assert sum(new int[]{1}) == 1;
        System.out.println("ok: sum {1} == 1");

        assert sum(new int[]{1, 2, 3, 4}) == 10;
        assert sum(new int[]{5, 6, 7, 8}) == 26;
		assert sum(new int[]{1,1,1,1,1}) == 5;
		assert sum(new int[]{1,2,1,2,1}) == 7;

 
        assert sum(null) == 0;

        System.out.println("sumTest passed.");
    }


	public static int[] bubblesort(int[] array) {
			int temp;
			for(int i=1; i<array.length; i++) {
				for(int j=0; j<array.length-i; j++) {
					if(array[j]>array[j+1]) {
						temp=array[j];
						array[j]=array[j+1];
						array[j+1]=temp;
					}
					
				}
			}
			return array;
	}

	static public void bubblesortTest() {
		assert Arrays.equals(bubblesort(new int[]{1,2}),new int[]{1,2});
		//System.out.println("ok: bubblesortTest {1,2} == {1,2}");

		assert Arrays.equals(bubblesort(new int[]{2,5,6,7,9,4}),new int[]{2,4,5,6,7,9});
		assert Arrays.equals(bubblesort(new int[]{9,8,7,6,5,4}),new int[]{4,5,6,7,8,9});
		assert Arrays.equals(bubblesort(new int[]{5,6,3,1,2}),new int[]{1,2,3,5,6});
		assert Arrays.equals(bubblesort(new int[]{100,200,0,5,8,2}),new int[]{0,2,5,8,100,200});


		System.out.println("sumTest passed.");
	}

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
