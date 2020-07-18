package com.example.tagscoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class TagscoringApplication {

    public static void printArraylist(double[] a) {
        for(int i=0; i<a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static double calcEuclideanDist(double[] p1, double[] p2) {

        double sum = 0;
        double dist;

        for (int i = 0; i < p1.length; i++) {
            sum += Math.pow(p1[i] - p2[i], 2);
        }

        dist = Math.sqrt(sum);

        return dist;
    }

    public static double[] normalization(double[] p, double[] min_p, double[] max_p) {
        double[] norm_p = new double[p.length];

        for (int i = 0; i < p.length; i++) {

            double norm_i;

            // normalize
            norm_i = (p[i] - min_p[i]) / (max_p[i] - min_p[i]);

            // append normalized number
            norm_p[i] = norm_i;
        }

        return norm_p;
    }

    public static void main(String[] args) {
        SpringApplication.run(TagscoringApplication.class, args);

        /*
        1. 태그 스코어
        */
        System.out.println("\n\n######### 1 태그 스코어링");

        // p1 포맷 = {해쉬태그가 사용된 사용된 게시물 개수, 해쉬태그를 사용한 게시물의 댓글수 총합, 해쉬태그를 사용한 게시물의 조회수 총합}
        double[] p1 = {100, 2000, 6000}, origin = {0, 0, 0};
        double[] min_p1 = {0, 0, 0}, max_p1 = {400, 4200, 10000};
        double score;

        System.out.println("* point => ");
        printArraylist(p1);
        System.out.println();

        // p1 정규화
        p1 = normalization(p1, min_p1, max_p1);

        System.out.println("* normalized point => ");
        printArraylist(p1);
        System.out.println();

        // p1과 원점 사이의 유클리디안 거리 계산
        score = calcEuclideanDist(p1, origin);

        System.out.println("* score = " + score);




        /*
        2. 본문에서 해쉬태그 파싱
        */

        String content;

        content = "어쩌구 저쩌구~~!!@!   #안드로이드 #개발자 #급구";

        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");

        Matcher mat = MY_PATTERN.matcher(content);

        List<String> tags = new ArrayList<String>();

        while (mat.find()) {
            tags.add(mat.group(1));
        }

        System.out.println("\n\n######### 2 본문에서 해쉬태그(#) 파싱하기");
        System.out.println("본문 => "+content);
        System.out.println("해쉬태그 어레이리스트 => "+tags.toString());

    }

}
