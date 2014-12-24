package com.baosight.scc.ec.image;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import org.springframework.util.StringUtils;

public class ImageTools {

	/**
	 * ImageMagick的路径
	 */
	public static String imageMagickPath = "C://Program Files (x86)//GraphicsMagick-1.3.19-Q8";
	static {
		/**
		 * 获取ImageMagick的路径
		 */
		// Properties prop = new PropertiesFile().getPropertiesFile();
		// linux下不要设置此值，不然会报错
		// imageMagickPath = prop.getProperty("imageMagickPath");
	}

	/**
	 * 根据坐标裁剪图片
	 * @param srcPath 要裁剪图片的路径 
	 * @param newPath 裁剪图片后的路径 
	 * @param x 起始横坐标 
	 * @param y 起始挫坐标 
	 * @param x1 结束横坐标 
	 * @param y1 结束挫坐标 
	 */
	public static void cutImage(String srcPath, String newPath, int x, int y,
			int x1, int y1) throws Exception {
		int width = x1 - x;
		int height = y1 - y;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		/**
		 * width：裁剪的宽度
		 * height：裁剪的高度
		 * x：裁剪的横坐标
		 * y：裁剪的挫坐标
		 */
		op.crop(width, height, x, y);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd(true);
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			// linux下不要设置此值，不然会报错
			convert.setSearchPath(imageMagickPath);
		}
		convert.run(op);
	}

	/** 
	 * 根据尺寸缩放图片 
	 * @param width 缩放后的图片宽度 
	 * @param height 缩放后的图片高度 
	 * @param srcPath 源图片路径 
	 * @param newPath 缩放后图片的路径 
	 * @param type 1为比例处理，2为大小处理，如（比例：1024x1024,大小：50%x50%）
	 */
	public static String zoomImage(int width, int height, String srcPath,
			String newPath, int type, String quality) throws Exception {
		IMOperation op = new IMOperation();
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = "";
		if (type == 1) {
			//按像素
			raw = width + "x" + height + "^";
		} else {
			//按像素百分比
			raw = width + "%x" + height + "%";
		}
		op.addRawArgs("-sample", raw);
		if ((quality != null && quality.equals(""))) {
			op.addRawArgs("-quality", quality);
		}
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			//linux下不要设置此值，不然会报错
			cmd.setSearchPath(imageMagickPath);
		}

		try {
			cmd.run(op, srcPath, newPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newPath;
	}
	
	/**
	 * 根据尺寸缩放图片
	 * @param width  缩放后的图片宽度
	 * @param srcPath   源图片路径
	 * @param newPath   缩放后图片的路径
	 */
	public static void zoomImage(int width, String srcPath, String newPath)
			throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);

		op.resize(width, null);
		op.addImage(newPath);

		ConvertCmd convert = new ConvertCmd(true);

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			// linux下不要设置此值，不然会报错
			convert.setSearchPath(imageMagickPath);
		}

		convert.run(op);
	}
	
	/**
	 * 根据尺寸缩放图片
	 * @param width  缩放后的图片宽度
	 * @param height  缩放后的图片高度
	 * @param srcPath   源图片路径
	 * @param newPath   缩放后图片的路径
	 */
	public static void zoomImage(int width, int height, String srcPath,
			String newPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);

		op.resize(width, height);
		op.addImage(newPath);

		ConvertCmd convert = new ConvertCmd(true);

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			// linux下不要设置此值，不然会报错
			convert.setSearchPath(imageMagickPath);
		}

		convert.run(op);
	}

	/**
	 * 给图片加水印
	 * 
	 * @param srcPath
	 *            源图片路径
	 */
	public static void addWaterMark(String srcPath) throws Exception {
		IMOperation op = new IMOperation();
		//op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8").draw(
		//		"text 100,100 baosteel.com");
		op.font("Arial").gravity("southeast").pointsize(100).fill("#BCBFC8").draw("text 100,100 www.baosteel.com");
		op.addImage();
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		ConvertCmd cmd = new ConvertCmd(true);
		if (osName.indexOf("win") != -1) {
			//linux下不要设置此值，不然会报错
			cmd.setSearchPath(imageMagickPath);
		}

		try {
			cmd.run(op, srcPath, srcPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void blurImage(String srcPath, String newPath)
			throws Exception {
		IMOperation op = new IMOperation();
		
		//op.blur(new Double(100), new Double(10));//模糊
		//op.border(100).bordercolor("#000000");//边框
		//op.monochrome();//黑白
		//op.modulate(new Double(10), new Double(10), new Double(10));//亮度,饱和度,色调
		
		
		//op.fill("#FFFFFF").draw("circle 1500,500 1500,0");
		op.gravity("NorthWest").fill("red").draw("rectangle 1000, 200 1500, 1000");
		op.font("Arial").pointsize(100).fill("#000000").draw("text 1000,500 www.baosteel.com");
		//op.dissolve(50);
		
		//op.transparent("red");
		
		op.addImage();
		op.addImage();
		
		ConvertCmd convert = new ConvertCmd(true);

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			// linux下不要设置此值，不然会报错
			convert.setSearchPath(imageMagickPath);
		}

		convert.run(op, srcPath, newPath);
	}
	
	public static void zoomImageBycode(String sysCode, String srcPath) {
		try {
			if (Constants.moduleCode_FC.equals(sysCode)) {
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_200), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_200));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_50), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_50));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_120), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_120));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_300),
						Integer.parseInt(Constants.imageSize_300), srcPath,
						getNewPath(srcPath, "_" + Constants.imageSize_300));
			} else if (Constants.moduleCode_PM.equals(sysCode)) {
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_350), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_350));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_200), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_200));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_50), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_50));
			} else if (Constants.moduleCode_EC.equals(sysCode)) {
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_1000), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_1000));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_600), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_600));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_300), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_300));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_100), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_100));
			} else if (Constants.picType_YYZZ.equals(sysCode)||Constants.picType_ORG.equals(sysCode)||Constants.picType_TAX.equals(sysCode)) {
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_206), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_206));
			} else if (Constants.picType_LOG.equals(sysCode)) {
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_256), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_256));
			} else {
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_200), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_200));
				ImageTools.zoomImage(Integer.parseInt(Constants.imageSize_50), srcPath, getNewPath(srcPath, "_" + Constants.imageSize_50));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getNewPath(String inString, String newPattern) {
		String oldPattern = ".";
		return StringUtils
				.replace(inString, oldPattern, newPattern + oldPattern);
	}

}
