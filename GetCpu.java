public class GetCpu{
	
    public static String getSystemProperty(String propName) {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        }
        catch (IOException ex) {
            Log.e("Jasi", "Unable To Read Prop " + propName, ex);
            return null;
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    Log.e("Jasi", "Exception While Closing InputStream", e);
                }
            }
        }
        return line;
    }
	
	// call this function
    private String letsgetcpu(){

        Log.v("Jasi","Checking CPU Architecture...");

        String arch;
	boolean is64 = false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) // below lollipop all are 32bit cpu
        {
            arch = Build.CPU_ABI;
        } else
        {
            arch = Build.SUPPORTED_ABIS[0];
            String s = getSystemProperty("ro.product.cpu.abilist");
            Log.v("Jasi","Supported CPUs :- "+s);
            is64 = s.contains("64");
        }

        arch = arch.substring(0, 3).toUpperCase(Locale.US);

        String rarc = "UNKNOWN";

        if (arch.equals("ARM")) {
            rarc = !is64 ? "arm" : "arm64";
        }else if (arch.equals("X86")){
	    rarc = !is64 ? "x86" : "x86_64";
        }else if (arch.equals("MIP")){
	    rarc = !is64 ? "mips" : "mips64";
        }
    
	Log.v("Jasi","CPU Detected :- "+rarc);

        Log.v("Jasi","Is 64 Bit CPU ? :- "+is64);

	/* results arm,arm64,x86,x86_64,mips,mips64 (mips is very rare and mips64 is almost zero but google support dex2oat of it so i added)

	Name of CPU are proper same name according to google uses and same folder can be found in /data/dalvik-cache/x86_64 and 
	/data/app/app_pkg_name/oat/x86_64 etc*/

	return rarc;
    }

}
