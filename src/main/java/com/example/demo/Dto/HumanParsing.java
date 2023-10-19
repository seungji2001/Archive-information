package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

public class HumanParsing {

    @Builder
    @Data
    public static class ResponseDto{
        private List<Person> personList;
    }

    @Builder
    @Data
    public static class Person{
        private String num;
        private List<Integer> position;
        private List<List<Integer>> hatMask;
        private Color hatColor;
        private List<List<Integer>> hairMask;
        private Color hairColor;
        private List<List<Integer>> upclothMask;
        private Color upclothColor;
        private List<List<Integer>> dressMask;
        private Color dressColor;
        private List<List<Integer>> coatMask;
        private Color coatColor;
        private List<List<Integer>> pantsMask;
        private Color pantsColor;
        private List<List<Integer>> skirtMask;
        private Color skirtColor;
    }

    @Builder
    @Data
    public static class PersonOriginalList{
        private List<PersonOriginal> personOriginals;
    }

    @Data
    @Builder
    public static class PersonOriginal{
        private String num;
        String position;
        String hatMask;
        String hatColor;
        String hairMask;
        String hairColor;
        String upclothMask;
        String upclothColor;
        String dressMask;
        String dressColor;
        String coatMask;
        String coatColor;
        String pantsMask;
        String pantsColor;
        String skirtMask;
        String skirtColor;
    }
}
