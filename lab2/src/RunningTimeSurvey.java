import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.io.File;
import java.lang.reflect.Method;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class RunningTimeSurvey {
	//             task name            function name             run times upper
	static String[][] taskList = {
			{ "LinearTimeTest",         "linearTime",            "10000000" },
			{ "LinearTimeTest",         "linearTimeCollections", "10000000" },
			{ "NlognTimeTest",       	"NlognTime",             "1000000"},
			{ "QuadraticTimeTest",   	"QuadraticTime",         "1000000"},
			{ "CubicTimeTest",       	"CubicTime",             "10000"},
			{ "ExponentialTimeTest", 	"ExponentialTime",       "10"},
			{ "FactorialTimeTest",   	"FactorialTime",         "10"}
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String osName = System.getProperty("os.name");
		System.out.println(osName);
		try {
			File xlsFile = new File("RunningTimeSurvey.xls");
			// Create a workbook
			WritableWorkbook workbook;
			workbook = Workbook.createWorkbook(xlsFile);

			// Create a worksheet
			WritableSheet sheet = workbook.createSheet("RunningTime", 0);
			// the first row
			for (int j = 1, n = 1; j <= 8; j++) {
				n = 10 * n;
				sheet.addCell(new Label(j + 1, 0, "n = " + n));
			}
			for (int i = 0; i < taskList.length; i++) {
				String[] taskInfo = taskList[i];

				Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
				Method method = me.getMethod(taskInfo[1], int.class);
				int upper = Integer.parseInt(taskInfo[2]);
				sheet.addCell(new Label(0, i + 1, taskInfo[0]));
				sheet.addCell(new Label(1, i + 1, taskInfo[1]));

				for (int j = 1, n = 1; Math.pow(10, j) <= upper; j++) {
					n = 10 * n;
					Long time = (Long) method.invoke(null, n);
					// 向工作表中添加数据
					sheet.addCell(new Label(j + 1, i + 1, time.toString()));
				}

			}
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static long linearTimeCollections(int n) {
		ArrayList<Long> arrayList = new ArrayList<Long>(n);
		generateArrayList(n, arrayList);
		long timeStart = System.currentTimeMillis();
		getMax(n, arrayList);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static long linearTime(int n) {
		long[] list = new long[n];
		generateList(n, list);
		long timeStart = System.currentTimeMillis();
		getMax(n, list);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static long getMax(long n, long[] list) {
		long max = list[0];
		for (int i = 1; i < n; i++) {
			if (list[i] > max) {
				max = list[i];
			}
		}
		return max;
	}

	public static void generateList(int n, long[] list) {
		for (int i = 0; i < n; i++) {
			list[i] = i;
		}
		// shuffle
		Random rnd = new Random();
		for (int i = list.length; i > 1; i--) {
			int j = rnd.nextInt(i);
			long temp = list[j];
			list[j] = list[i - 1];
			list[i - 1] = temp;
		}
	}

	public static void generateArrayList(int n, ArrayList<Long> arrayList) {
		for (long i = 0; i < n; i++) {
			arrayList.add(i);
		}
		// shuffle
		Collections.shuffle(arrayList);
	}

	public static long getMax(long n, ArrayList<Long> arrayList) {
		long max = arrayList.get(0);
		for (int i = 1; i < n; i++) {
			if (arrayList.get(i) > max) {
				max = arrayList.get(i);
			}
		}
		return max;
	}

	//NlognTimeTest

	public static long NlognTime(int n) {
		long[] list = new long[n];
		generateList(n,list);
		long[] temp_array = new long[n];
		long timeStart = System.currentTimeMillis();
		mergeSort(list,temp_array,0,n-1);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static void mergeSort(long[] list, long[] temp_array, int left, int right) {
		if(left<right){
			int center = (left+right)/2;
			mergeSort(list,temp_array,left,center);
			mergeSort(list,temp_array,center+1,right);
			merge(list,temp_array,left,center+1,right);
		}
	}

	public static void merge(long[] list, long[] temp_array, int left_pos, int right_pos, int right_end){
		int left_end = right_pos - 1;
		int temp_pos = left_pos;
		int begin = left_pos;
		int end = right_end;

		while(left_pos<=left_end&&right_pos<=right_end)
		{
			if(list[left_pos]<list[right_pos])
				temp_array[temp_pos++] = list[left_pos++];
			else
				temp_array[temp_pos++] = list[right_pos++];
		}

		while(left_pos<=left_end)
			temp_array[temp_pos++] = list[left_pos++];

		while(right_pos<=right_end)
			temp_array[temp_pos++] = list[right_pos++];

		for(int i = begin;i<end+1;i++)
			list[i] = temp_array[i];
	}



	//QuadraticTimeTest
	public static long QuadraticTime(int n) {
		long []list = new long[n];
		generateList(n,list);
		long timeStart = System.currentTimeMillis();
		bubbleSort(n,list);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static void bubbleSort(int n, long[] list) {
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (list[j] > list[j + 1]) {
					// swap
					long tmp = list[j + 1];
					list[j + 1] = list[j];
					list[j] = tmp;
				}
			}
		}
	}



	//CubicTimeTest
	public static long CubicTime(int n) {
		ArrayList<ArrayList<Long>>lists = new ArrayList<>();
		for(int i = 0;i<n;i++){
			ArrayList<Long>list = new ArrayList<>();
			generateArrayList(n, list);
			lists.add(list);
		}
		long timeStart = System.currentTimeMillis();
		CubicTest(n,lists);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}
	public static boolean CubicTest(int n, ArrayList<ArrayList<Long>>lists){
		boolean disjoint = true;
		ArrayList<Long>list = lists.get(0);
		for(int j = 1;j<n;j++){
			for(int k = 0;k<n;k++){
				ArrayList<Long> l = lists.get(j);
				for(int p = 0;p<n;p++) {
					if(list.get(k)==l.get(p)) {
						disjoint = false;
						break;
					}
				}
			}
		}
		return disjoint;
	}



	//ExponentialTimeTest
	public static long ExponentialTime(int n) {
		long list[] = new long[n];
		long timeStart = System.currentTimeMillis();
		ExponentialTest(n,list);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static void ExponentialTest(int n, long list[]){
		if(n == 0) return;
			list[n-1] = 0;
			ExponentialTest(n-1,list);
			list[n-1] = 1;
			ExponentialTest(n-1,list);
	}



	//FactorialTimeTest
	public static long FactorialTime(int n) {
		long timeStart = System.currentTimeMillis();
		Factorial(n);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static long Factorial(int n) {
		if (n == 1)
			return 1;
		else {
			long sum = 0;
			for (int i = 0; i < n; i++) {
				sum += Factorial(n - 1);
			}
			return sum;
		}

	}

}
