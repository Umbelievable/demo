package com.example.demo.service;


import com.example.demo.model.Recommend;
import com.example.demo.model.review.*;
import com.example.demo.repository.review.*;
import com.example.demo.util.PagingUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private BedroomReviewRepository bedroomReviewRepository;
    @Autowired
    private KitchenReviewRepository kitchenReviewRepository;
    @Autowired
    private LibraryReviewRepository libraryReviewRepository;
    @Autowired
    private LivingroomReviewRepository livingroomReviewRepository;
    @Autowired
    private StorageReviewRepository storageReviewRepository;
    @Autowired
    private PurchaseService purchaseService;


    public Map getReviewsPaging(Integer p_num, int pd_no, String subCateNo, String categoryNo) throws IOException {
        Map result = null;

        PagingUtil pu = new PagingUtil(p_num, 10, 10); // ($1:표시할 현재 페이지, $2:한페이지에 표시할 글 수, $3:한 페이지에 표시할 페이지 버튼의 수 )

        System.out.println("p_num : "+p_num);
        System.out.println(pu.toString());

        List<?> reviewlist;
        int allCount;

        switch (categoryNo) {
            case "침실가구":
                List<BedroomReview> list = bedroomReviewRepository.findFromTo(subCateNo, pd_no, pu.getObjectStartNum(), pu.getObjectCountPerPage());

                allCount = bedroomReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pd_no, subCateNo, categoryNo);
                pu.setObjectCountTotal(allCount);
                pu.setCalcForPaging();

                if (list == null || list.size() == 0) {
                    return null;
                }

                result = new HashMap<>();
                result.put("pagingData", pu);
                result.put("list", list);
                break;

            case "주방가구":
                List<KitchenReview> kitchenReviewList = kitchenReviewRepository.findFromTo(subCateNo, pd_no, pu.getObjectStartNum(), pu.getObjectCountPerPage());

                allCount = kitchenReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pd_no, subCateNo, categoryNo);
                pu.setObjectCountTotal(allCount);
                pu.setCalcForPaging();

                if (kitchenReviewList == null || kitchenReviewList.size() == 0) {
                    return null;
                }



                result = new HashMap<>();
                result.put("pagingData", pu);
                result.put("list", kitchenReviewList);
                break;

            case "서재/사무용가구":
                List<LibraryReview> libraryReviewList = libraryReviewRepository.findFromTo(subCateNo, pd_no, pu.getObjectStartNum(), pu.getObjectCountPerPage());

                allCount = libraryReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pd_no, subCateNo, categoryNo);
                pu.setObjectCountTotal(allCount);
                pu.setCalcForPaging();

                if (libraryReviewList == null || libraryReviewList.size() == 0) {
                    return null;
                }

                result = new HashMap<>();
                result.put("pagingData", pu);
                result.put("list", libraryReviewList);
                break;

            case "거실가구":
                List<LivingroomReview> livingroomReviewList = livingroomReviewRepository.findFromTo(subCateNo, pd_no, pu.getObjectStartNum(), pu.getObjectCountPerPage());

                allCount = livingroomReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pd_no, subCateNo, categoryNo);
                pu.setObjectCountTotal(allCount);
                pu.setCalcForPaging();

                if (livingroomReviewList == null || livingroomReviewList.size() == 0) {
                    return null;
                }

                result = new HashMap<>();
                result.put("pagingData", pu);
                result.put("list", livingroomReviewList);
                break;

            case "수납가구":
                List<StorageReview> storageReviewList = storageReviewRepository.findFromTo(subCateNo,pd_no, pu.getObjectStartNum(), pu.getObjectCountPerPage());

                allCount = storageReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pd_no, subCateNo, categoryNo);
                pu.setObjectCountTotal(allCount);
                pu.setCalcForPaging();

                if (storageReviewList == null || storageReviewList.size() == 0) {
                    return null;
                }

                result = new HashMap<>();
                result.put("pagingData", pu);
                result.put("list", storageReviewList);
                break;

        }


        return result;
    }


    //리뷰 작성
    public ResponseEntity<?> createReview(Review review, int pdNo, String subcate, String categoryNo){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        String predict = "";

        //리뷰 작성 여부
        purchaseService.changeReviewWrite(review.getCustomerId(), pdNo, subcate, categoryNo);
        switch (categoryNo){

            case "침실가구":
                BedroomReview bedroomReview = new BedroomReview();
                bedroomReview.setReviewNo(bedroomReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pdNo, subcate, categoryNo)+1);
                bedroomReview.setPdNo(pdNo);
                bedroomReview.setSubcateNo(subcate);
                bedroomReview.setCategoryNo(categoryNo);
                bedroomReview.setStar(review.getStar());
                bedroomReview.setCustomerId(review.getCustomerId());
                bedroomReview.setReview(review.getReview());
                bedroomReview.setReviewDate(date);

                if(subcate.equals("침대") && pdNo == 0){
                    predict = getPredict(pdNo, subcate, categoryNo, review.getReview());
                }
                bedroomReview.setPredict(predict);
                bedroomReviewRepository.save(bedroomReview);
                return new ResponseEntity<>(bedroomReview, HttpStatus.CREATED);
            case "주방가구":
                KitchenReview kitchenReview = new KitchenReview();
                kitchenReview.setReviewNo(kitchenReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pdNo, subcate, categoryNo)+1);
                kitchenReview.setPdNo(pdNo);
                kitchenReview.setSubcateNo(subcate);
                kitchenReview.setCategoryNo(categoryNo);
                kitchenReview.setStar(review.getStar());
                kitchenReview.setCustomerId(review.getCustomerId());
                kitchenReview.setReview(review.getReview());
                kitchenReview.setReviewDate(date);
                kitchenReviewRepository.save(kitchenReview);
                return new ResponseEntity<>(kitchenReview, HttpStatus.CREATED);
            case "서재/사무용가구":
                LibraryReview libraryReview = new LibraryReview();
                libraryReview.setReviewNo(kitchenReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pdNo, subcate, categoryNo)+1);
                libraryReview.setPdNo(pdNo);
                libraryReview.setSubcateNo(subcate);
                libraryReview.setCategoryNo(categoryNo);
                libraryReview.setStar(review.getStar());
                libraryReview.setCustomerId(review.getCustomerId());
                libraryReview.setReview(review.getReview());
                libraryReview.setReviewDate(date);
                libraryReviewRepository.save(libraryReview);
                return new ResponseEntity<>(libraryReview, HttpStatus.CREATED);
            case "거실가구":
                LivingroomReview livingroomReview = new LivingroomReview();
                livingroomReview.setReviewNo(livingroomReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pdNo, subcate, categoryNo)+1);
                //System.out.println(livingroomReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pdNo, subcate, categoryNo)+1);
                livingroomReview.setPdNo(pdNo);
                livingroomReview.setSubcateNo(subcate);
                livingroomReview.setCategoryNo(categoryNo);
                livingroomReview.setStar(review.getStar());
                livingroomReview.setCustomerId(review.getCustomerId());
                livingroomReview.setReview(review.getReview());
                livingroomReview.setReviewDate(date);

                if(subcate.equals("소파") && pdNo == 0){
                    predict = getPredict(pdNo, subcate, categoryNo, review.getReview());
                }
                livingroomReview.setPredict(predict);

                livingroomReviewRepository.save(livingroomReview);
                return new ResponseEntity<>(livingroomReview, HttpStatus.CREATED);
            case "수납가구":
                StorageReview storageReview = new StorageReview();
                storageReview.setReviewNo(storageReviewRepository.countByPdNoAndSubcateNoAndCategoryNo(pdNo, subcate, categoryNo)+1);
                storageReview.setPdNo(pdNo);
                storageReview.setSubcateNo(subcate);
                storageReview.setCategoryNo(categoryNo);
                storageReview.setStar(review.getStar());
                storageReview.setCustomerId(review.getCustomerId());
                storageReview.setReview(review.getReview());
                storageReview.setReviewDate(date);

                if(subcate.equals("선반") && pdNo == 2){
                    predict = getPredict(pdNo, subcate, categoryNo, review.getReview());
                }
                storageReview.setPredict(predict);

                storageReviewRepository.save(storageReview);
                return new ResponseEntity<>(storageReview, HttpStatus.CREATED);
            default:
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //리뷰에서 해시태그 찾기
    public List<?> getReviewByHashtag(int pdno, String subcateNo, String cateNo, String hashtag){
        switch(cateNo){
            case "침실가구":
                return bedroomReviewRepository.findAllByPdNoAndSubcateNoAndCategoryNoAndReviewIgnoreCaseContaining(pdno, subcateNo, cateNo, hashtag);
            case "주방가구":
                return kitchenReviewRepository.findAllByPdNoAndSubcateNoAndCategoryNoAndReviewIgnoreCaseContaining(pdno, subcateNo, cateNo, hashtag);
            case "서재/사무용가구":
                return libraryReviewRepository.findAllByPdNoAndSubcateNoAndCategoryNoAndReviewIgnoreCaseContaining(pdno, subcateNo, cateNo, hashtag);
            case "거실가구":
                return livingroomReviewRepository.findAllByPdNoAndSubcateNoAndCategoryNoAndReviewIgnoreCaseContaining(pdno, subcateNo, cateNo, hashtag);
            case "수납가구":
                return storageReviewRepository.findAllByPdNoAndSubcateNoAndCategoryNoAndReviewIgnoreCaseContaining(pdno, subcateNo, cateNo, hashtag);
            default:
           return null;

        }
    }

    public String getPredict(int pdNo, String subcateNo, String categoryNo, String review){
        String predict = null;
        ModelAndView mav = new ModelAndView();
        String url = "http://0.0.0.0:5000/rec/predict";
        String sb = "";
        String json = "";


        try {

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);
            conn.setDoInput (true);
            conn.setRequestMethod("POST");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pdNo", pdNo);
            jsonObject.put("subcateNo", subcateNo);
            jsonObject.put("categoryNo", categoryNo);
            jsonObject.put("review", review);
            json = jsonObject.toString();

            conn.setRequestProperty("Content-type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();


            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            predict = sb.toString();
            br.close();

            //System.out.println("" + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return predict;
    }




}
