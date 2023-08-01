package com.zukxu.java8.test.testdemo.loop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zukxu
 * CreateTime: 2021/6/22 0022 19:34
 */
public class TestLoop {
    public static void main(String[] args) {
        System.out.print("[");
        for (int i = 0; i <= 100; i++) {
            if ((i + 1) % 4 == 0) {

                System.out.print(i + ", ");
            }
        }
        System.out.print("]");
		/*List<RecordDTO> dtoList = initArr();
		for (RecordDTO dto : dtoList) {
			System.out.println(dto.toString());
			for (int i = 0; i < dto.getContent().size(); i++) {
				System.out.print(dto.getContent().get(i).getMid());
				System.out.print("---------");
				if ((i + 1) % 4 == 0 || (i + 1) == dto.getContent().size()) {
					System.out.println("row");
				}
			}
		}*/
    }

    public static List<RecordDTO> initArr() {
        List<RecordDTO> contentList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            RecordDTO recordDTO = new RecordDTO().setMid(i).setTitle("测试" + i);
            List<Record> list = new ArrayList<>();
            for (int j = 100; j < 110; j += 2) {
                Record record = new Record().setMid(j * i).setTitle("test" + j).setUrl("www").setImg("icon").setDescription("qqq" + j).setIsHot(false);
                list.add(record);
            }
            recordDTO.setContent(list);
            contentList.add(recordDTO);
        }

        return contentList;

    }

}
