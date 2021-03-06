package com.example.demo.repository.item;

import com.example.demo.model.item.Item;
import com.example.demo.model.item.ItemAbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoRepositoryBean
public interface ItemRepository<T extends ItemAbs, ID extends Serializable> extends JpaRepository<T, ID> {
    //
    T findItemByPdNoAndCateNoAndSubcateNo(int pdNo, String cateNo, String subcateNo);
    List<T> findAllBySubcateNo(String subcateNo);

    //서브카테, 상품명, 쇼핑몰 전체에서
    List<T> findAllByPdTitleIgnoreCaseContainingOrPdMallIgnoreCaseContaining(String searchKeyword, String searchKeyword1);

    List<T> findAllByCateNoIgnoreCaseContainingOrSubcateNoIgnoreCaseContainingOrPdTitleIgnoreCaseContainingOrPdMallIgnoreCaseContaining(String searchKeyword1, String searchKeyword2, String searchKeyword3, String searchKeyword4);


    public default Item transformToItem(T temp){
        Item item =  new Item();
        item.setPdNo(temp.getPdNo());
        item.setCateNo(temp.getCateNo());
        item.setSubcateNo(temp.getSubcateNo());
        item.setPdTitle(temp.getPdTitle());
        item.setPdHref(temp.getPdHref());
        item.setPdImg(temp.getPdImg());
        item.setPdPrice(temp.getPdPrice());
        item.setPdMall(temp.getPdMall());

        return item;
    }

    public default Item findByPK(int pdNo, String cateNo, String subcateNo){

        return transformToItem(findItemByPdNoAndCateNoAndSubcateNo(pdNo, cateNo, subcateNo));
    }

    public default List<Item> getItemsBySubcate(String subcateNo){
        List<T> list = findAllBySubcateNo(subcateNo);
        List<Item> item = new ArrayList<>();
        if(!list.isEmpty()){
            for(T it : list){
                item.add(transformToItem(it));
            }
        }
        return item;
    }


    public default List<Item> searchAllItem(String searchKeyword){
        List<T> list = findAllByCateNoIgnoreCaseContainingOrSubcateNoIgnoreCaseContainingOrPdTitleIgnoreCaseContainingOrPdMallIgnoreCaseContaining(searchKeyword, searchKeyword, searchKeyword, searchKeyword);
        List<Item> item = new ArrayList<>();
        if(!list.isEmpty()){
            for(T it : list){
                item.add(transformToItem(it));
            }
        }
        return item;
    }

    public default List<Item> searchItemByCate(String subcateNo, String searchKeyword){
        List<T> list = findAllByPdTitleIgnoreCaseContainingOrPdMallIgnoreCaseContaining(searchKeyword, searchKeyword);
        List<Item> item = new ArrayList<>();
        if(!list.isEmpty()){
            for(T it : list){
                if(it.getSubcateNo().equals(subcateNo))
                    item.add(transformToItem(it));

            }
        }
        return item;
    }

}



