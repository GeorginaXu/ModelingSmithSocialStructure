function [G2] = socialize(G, t)

G2 = G;
while t > 0
    for u = 1:size(G,1)
        prob_matrix = computeProbability(G2, u);
        for v = 1:size(prob_matrix,1)
            uv_prob = prob_matrix(1,v);
            rand_p = rand;
            if(rand_p <= uv_prob && G2(u,v) == 0)
                G2(u,v) = 1; 
            end
        end
    end
    t = t-1;
end
            
            
    
