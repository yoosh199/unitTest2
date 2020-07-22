package service;

import domain.Champion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockServiceTest {

    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private MockService mockService;

    // ******************************************
    // 기본 mock test method 연습
    // ******************************************

    @Test
    public void 챔피언이름을가져오면_무조건_카이사를_리턴한다() {
        Champion champion = mock(Champion.class);//mock객체


        //champion.getName()을 호출하면 "카이사"를 리턴한다.
        when(champion.getName()).thenReturn("카이사");

        assertThat(champion.getName(), is("카이사"));
        System.out.println("champion : "+ champion.getName());





    }

    // 1. when, thenReturn을 사용하여 어떠한 챔피언 이름을 입력해도 베인을 리턴하도록 테스트하세요
    @Test
    public void 베인리턴() {
        Champion champion = mock(Champion.class);//mock객체
        champion.setName("모데카이저");
        System.out.println("champion : "+ champion.getName());

        when(champion.getName()).thenReturn("베인");
        assertThat(champion.getName(), is("베인"));
        System.out.println("champion : "+ champion.getName());


    }



    // 2. 챔피언 이름으로 야스오를 저장하면, doThrow를 사용하여 Exception이 발생하도록 테스트 하세요.
    @Test(expected = IllegalArgumentException.class)
    public void 야스오() {
        Champion champion = mock(Champion.class);//mock객체

        doThrow(new IllegalArgumentException()).when(champion).setName("야스오");
        champion.setName("야스오");


    }


    // 3. verify 를 사용하여 '미드' 포지션을 저장하는 프로세스가 진행되었는지 테스트 하세요.
    @Test
    public void 검증() {
        Champion champion = mock(Champion.class);//mock객체
        champion.setName("야스오");
        champion.setPosition("미드");
        champion.setName("야스오");


        verify(champion, times(1)).setPosition("미드");

        verify(champion,times(2)).setName("야스오");
    }


    // 4. champion 객체의 크기를 검증하는 로직이 1번 실행되었는지 테스트 하세요.
    @Test
    public void 크기검증() {
        Champion champion = mock(Champion.class);//mock객체
        List<Champion> mockChampions = mock(List.class);
        //List<Champion>  mockChampions = new ArrayList<Champion>();
        Champion champion1 = mock(Champion.class);
        champion.setName("리븐");
        champion.setPosition("탑");
        champion.setHasSkinCount(2);
        champion1.setName("쉬바나");
        mockChampions.add(champion);
        mockChampions.add(champion1);


        System.out.println("size" + mockChampions.size());
        verify(mockChampions, times(1)).size();




    }




    // 4-1. champion 객체에서 이름을 가져오는 로직이 2번 이상 실행되면 Pass 하는 로직을 작성하세요.
    @Test
    public void two(){
        Champion champion = mock(Champion.class);
        champion.setName("아리");
        champion.setPosition("미드");
        champion.setHasSkinCount(1);


        System.out.println("111" + champion.getName()+champion.getName()) ;
        System.out.println("111" + champion.getName()+champion.getName()) ;
        //System.out.println("111" + champion.getName()) ;
        verify(champion, atLeast(2)).getName();

    }

    // 4-2. champion 객체에서 이름을 가져오는 로직이 최대 3번 이하 실행되면 Pass 하는 로직을 작성하세요.
    @Test
    public void atMostThreeTime(){
        Champion champion = mock(Champion.class);
        champion.setName("람머스");
        champion.setPosition("정글");
        champion.setHasSkinCount(3);

        System.out.println("championName :: " + champion.getName() );
        System.out.println("championName :: " + champion.getName() );
        System.out.println("championName :: " + champion.getName() );
        verify(champion,atMost(3)).getName();



    }



    // 4-3. champion 객체에서 이름을 저장하는 로직이 실행되지 않았으면 Pass 하는 로직을 작성하세요.
    @Test
    public void pass(){
        Champion champion = mock(Champion.class);

        champion.setName("람머스");
        champion.setPosition("정글");
        champion.setHasSkinCount(3);


        verify(champion, never()).setName("케인");
        //verify(champion, never()).setPosition("정글");
        //verify(champion, never()).setName(anyString());






    }





    // 4-4. champion 객체에서 이름을 가져오는 로직이 200ms 시간 이내에 1번 실행되었는 지 검증하는 로직을 작성하세요.
    @Test
    public void twoHundredSecondIn() throws InterruptedException {
        Champion champion = mock(Champion.class);
        champion.setName("쉔");
        champion.setPosition("탑");
        champion.setHasSkinCount(2);
        //Thread.sleep(1000);
        System.out.println("name" + champion.getName());//깡통
        Thread.sleep(1000);
        verify(champion, timeout(200).atLeastOnce()).getName();
    }


    // ******************************************
    // injectmock test 연습
    // ******************************************

    @Test
    public void 챔피언정보들을Mocking하고Service메소드호출테스트() {
        when(mockService.findByName(anyString())).thenReturn(new Champion("루시안", "바텀", 5));
        String championName = mockService.findByName("애쉬").getName();
        assertThat(championName, is("루시안"));
        verify(mockRepository, times(1)).findByName(anyString());

    }

    // 1. 리산드라라는 챔피언 이름으로 검색하면 미드라는 포지션과 함께 가짜 객체를 리턴받고, 포지션이 탑이 맞는지를 테스트하세요
    @Test
    public void 리산드라검색미드랑가짜객체리턴포지션탑확인(){
        when(mockService.findByName("리산드라")).thenReturn(new Champion("리산드라", "탑", 3));
        Champion champion = mockService.findByName("리산드라");
        System.out.println("fff" + champion);
        assertThat(champion.getPosition(), is("탑"));


    }

    // 2. 2개 이상의 챔피언을 List로 만들어 전체 챔피언을 가져오는 메소드 호출시 그 갯수가 맞는지 확인하는 테스트 코드를 작성하세요.
    @Test
    public void twoChampionMakeList(){

        //List<Champion> champions = mock(List.class);
        List<Champion> champions = new ArrayList<Champion>();
        champions.add((new Champion("나르", "바텀", 2)));
        champions.add((new Champion("유미", "바텀", 3)));
        champions.add((new Champion("질리언", "바텀", 3)));

        when(mockService.findAllChampions()).thenReturn(champions);
        assertThat(mockService.findAllChampions().size(), is(3));
        verify(mockRepository, times(1)).findAll();

        //Champion champion = mock(Champion.class);
        //champion.setName("나르");
        //champion.setPosition("탑");
        //champion.setHasSkinCount(2);
        //Champion champion1 = mock(Champion.class);
        //champion1.setName("잔나");
        //champion1.setPosition("바텀");
        //champion1.setHasSkinCount(1);
        //champions.add(champion);
        //champions.add(champion1);




    }

    // 3. 챔피언을 검색하면 가짜 챔피언 객체를 리턴하고, mockRepository의 해당 메소드가 1번 호출되었는지를 검증하고, 그 객체의 스킨 개수가
    //    맞는지 확인하는 테스트코드를 작성하세요.
    @Test
    public void search(){


        when(mockService.findByName(anyString())).thenReturn(new Champion("오공","탑",1));
        assertThat(mockService.findByName("애니").getHasSkinCount(), is(1) );
        verify(mockRepository,times(1)).findByName("애니");
        verify(mockRepository,times(1)).findByName(anyString());

    }

    // 4. 2개 이상의 가짜 챔피언 객체를 List로 만들어 리턴하고, 하나씩 해당 객체를 검색한 뒤 검색을 위해 호출한 횟수를 검증하세요.
    @Test
    public void testInvocationCountWhenGetChampionList(){
        List<Champion> champions = new ArrayList<Champion>();
        champions.add((new Champion("나르", "바텀", 2)));
        champions.add((new Champion("유미", "바텀", 3)));
        champions.add((new Champion("질리언", "바텀", 3)));
        when(mockService.findAllChampions()).thenReturn(champions);
        assertThat(mockService.findAllChampions().get(0).getName(), is("나르"));
        assertThat(mockService.findAllChampions().size(), is(3));
        //findall 2번
        verify(mockRepository,times(2)).findAll();





    }


    //가장 많이 사용되는 테스트 중 하나로 BDD 방식에 기반한 테스트 방법 예제
    @Test
    public void 탐켄치를_호출하면_탐켄치정보를_리턴하고_1번이하로_호출되었는지_검증() {
        //given
        given(mockRepository.findByName("탐켄치")).willReturn(new Champion("탐켄치", "서폿", 4));
        //when
        Champion champion = mockService.findByName("탐켄치");
        //then
        verify(mockRepository, atLeast(1)).findByName(anyString());
        assertThat(champion.getName(), is("탐켄치"));

        //given
        given(mockRepository.findByName("신드라")).willReturn(new Champion("신드라", "미드", 3));
        //when
        Champion champion1 = mockService.findByName("신드라");
        //then
        verify(mockRepository, atMost(3)).findByName(anyString());
        assertThat(champion1.getName(), is("신드라"));


    }
}