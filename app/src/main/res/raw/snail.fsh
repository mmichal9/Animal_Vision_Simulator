#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


vec4 blackwhite(in vec4 col){

    vec3 c_r = vec3(0.3, 0.6, 0.1);
	vec3 c_g = vec3(0.3, 0.6, 0.1);
	vec3 c_b = vec3(0.3, 0.6, 0.1);

	vec3 rgb = vec3( dot(col.rgb,c_r), dot(col.rgb,c_g), dot(col.rgb,c_b) );
    return vec4(rgb, 1.);
}


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec4 tex1Color = texture2D(iChannel0, fragCoord.xy / iResolution.xy);

    const int kSize = 7;
    vec3 avg = vec3(0.0);
    for (int i=-kSize; i <= kSize; ++i) {
        for (int j = -kSize; j <= kSize; ++j) {
            avg = avg + texture2D(iChannel0, (fragCoord.xy + vec2(float(i), float(j)))/iResolution.xy).xyz;
        }
    }
    int area = (2*kSize + 1) * (2*kSize + 1);
    avg = avg.xyz/vec3(area); //interestingly divider is not x^2 but x^3. since the area of a box is x^2..

    vec4 col = vec4(avg, tex1Color.a);

	fragColor =  blackwhite(col);
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
